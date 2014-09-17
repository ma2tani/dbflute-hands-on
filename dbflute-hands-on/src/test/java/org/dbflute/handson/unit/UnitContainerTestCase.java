package org.dbflute.handson.unit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.seasar.dbflute.cbean.ListResultBean;
import org.seasar.dbflute.cbean.SpecifyQuery;
import org.seasar.dbflute.cbean.SubQuery;
import org.seasar.dbflute.cbean.coption.LikeSearchOption;
import org.seasar.dbflute.helper.HandyDate;
import org.seasar.dbflute.unit.seasar.ContainerTestCase;

import org.dbflute.handson.dbflute.cbean.MemberCB;
import org.dbflute.handson.dbflute.cbean.PurchaseCB;
import org.dbflute.handson.dbflute.exbhv.MemberBhv;
import org.dbflute.handson.dbflute.exbhv.PurchaseBhv;
import org.dbflute.handson.dbflute.exentity.Member;
import org.dbflute.handson.dbflute.exentity.Purchase;

/**
 * The base class of unit test cases with DI container. <br />
 * Use like this:
 * <pre>
 * ... YourTest <span style="color: #FD4747">extends</span> {@link UnitContainerTestCase} {
 * 
 *     public void test_yourMethod() {
 *         <span style="color: #3F7E5E">// ## Arrange ##</span>
 *         YourAction action = new YourAction();
 *         <span style="color: #FD4747">inject</span>(action);
 * 
 *         <span style="color: #3F7E5E">// ## Act ##</span>
 *         action.submit();
 * 
 *         <span style="color: #3F7E5E">// ## Assert ##</span>
 *         assertTrue(action...);
 *     }
 * }
 * </pre>
 * @author jflute
 */
public abstract class UnitContainerTestCase extends ContainerTestCase {

    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;

    /**
     * Adjust member's formalized date-time by keyword of member name. <br />
     * This method updates the first member that has the keyword in ordered id.
     * <pre>
     * e.g. update member that contains 'vi' in name
     *  adjustMember_FormalizedDatetime_FirstOnly(toDate("2005/10/05"), "vi")
     * </pre>
     * @param formalizedDatetime The date-time when the member was formalized. (NullAllowed: if null, update as null)
     * @param nameKeyword The keyword as contain-search to search members updated. (NotNull)
     * @param limit The limit count of updated member. (NotMinus & NotZero)
     */
    protected void adjustMember_FormalizedDatetime_FirstOnly(Date formalizedDatetime, String nameKeyword) {
        assertNotNull(nameKeyword);
        MemberCB cb = new MemberCB();
        cb.query().setMemberName_LikeSearch(nameKeyword, new LikeSearchOption().likeContain());
        cb.query().addOrderBy_MemberId_Asc();
        cb.fetchFirst(1);
        Member member = memberBhv.selectEntityWithDeletedCheck(cb);
        member.setFormalizedDatetime(toTimestamp(formalizedDatetime));
        memberBhv.updateNonstrict(member);
    }

    /**
     * Adjust the purchase date-time of the fixed member. <br />
     * Update the date-time as the member's formalized date-time in a week. <br />
     * This is for the seventh exercise of section 3. <br />
     * You can get the target data that has border line.
     */
    protected void adjustPurchase_PurchaseDatetime_fromFormalizedDatetimeInWeek() {
        Member adjustedMember;
        {
            MemberCB cb = new MemberCB();
            cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                }
            });
            // not to select existing in-week data
            Date fromDate = toDate("2005-01-01");
            Date toDate = toDate("2007-01-01");
            cb.query().setFormalizedDatetime_DateFromTo(fromDate, toDate);
            cb.fetchFirst(1);
            adjustedMember = memberBhv.selectEntityWithDeletedCheck(cb);
        }

        ListResultBean<Purchase> updatedPurchaseList;
        {
            PurchaseCB cb = new PurchaseCB();
            cb.query().setMemberId_Equal(adjustedMember.getMemberId());
            cb.query().scalar_Equal().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }).partitionBy(new SpecifyQuery<PurchaseCB>() {
                public void specify(PurchaseCB cb) {
                    cb.specify().columnMemberId();
                }
            });
            updatedPurchaseList = purchaseBhv.selectList(cb);
        }

        HandyDate handyDate = new HandyDate(adjustedMember.getFormalizedDatetime());
        Timestamp movedDatetime = handyDate.addDay(7).moveToDayTerminal().moveToSecondJust().getTimestamp();
        for (Purchase purchase : updatedPurchaseList) {
            purchase.setPurchaseDatetime(movedDatetime);
        }
        purchaseBhv.batchUpdateNonstrict(updatedPurchaseList, new SpecifyQuery<PurchaseCB>() {
            public void specify(PurchaseCB cb) {
                cb.specify().columnPurchaseDatetime();
            }
        });
    }

    /**
     * Adjust transaction isolation level to READ COMMITTED on this session. <br />
     * This method depends on the MySQL. (you cannot use for other DBMSs)
     * @throws SQLException
     */
    protected void adjustTransactionIsolationLevel_ReadCommitted() throws SQLException {
        DataSource dataSource = getDataSource();
        Connection conn = null;
        Statement st = null;
        try {
            conn = dataSource.getConnection();
            st = conn.createStatement();
            st.execute("set SESSION transaction isolation level READ COMMITTED");
        } finally {
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
