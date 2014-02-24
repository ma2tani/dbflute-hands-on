package org.dbflute.quickstage.dbflute.thematic;

import org.dbflute.quickstage.dbflute.cbean.MemberCB;
import org.dbflute.quickstage.dbflute.cbean.PurchaseCB;
import org.dbflute.quickstage.dbflute.exbhv.MemberBhv;
import org.dbflute.quickstage.dbflute.exbhv.PurchaseBhv;
import org.dbflute.quickstage.dbflute.exentity.Member;
import org.dbflute.quickstage.unit.UnitContainerTestCase;
import org.seasar.dbflute.cbean.ListResultBean;
import org.seasar.dbflute.cbean.SubQuery;

/**
 * @author jflute
 */
@SuppressWarnings("unused")
public class PrototypeOfDemoTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;

    public void test_demo() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                subCB.query().setPaymentCompleteFlg_Equal_False();
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
        }
    }

    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
}
