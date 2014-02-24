/*
 * Copyright(c) DBFlute TestCo.,TestLtd. All Rights Reserved.
 */
package org.dbflute.quickstage.dbflute.cbean.cq;

import org.dbflute.quickstage.dbflute.cbean.PurchaseCB;
import org.dbflute.quickstage.dbflute.cbean.cq.bs.BsMemberCQ;
import org.seasar.dbflute.cbean.ConditionQuery;
import org.seasar.dbflute.cbean.SubQuery;
import org.seasar.dbflute.cbean.sqlclause.SqlClause;


/**
 * The condition-query of MEMBER.
 * <p>
 * You can implement your original methods here.
 * This class is NOT overrided when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 */
public class MemberCQ extends BsMemberCQ {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    // You should NOT touch with this constructor.
    /**
     * Constructor.
     * @param childQuery Child query as abstract class. (Nullable: If null, this is base instance.)
     * @param sqlClause SQL clause instance. (NotNull)
     * @param aliasName My alias name. (NotNull)
     * @param nestLevel Nest level.
     */
    public MemberCQ(ConditionQuery childQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(childQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                       Arrange Query
    //                                                                       =============
    // You can make your arranged query methods here.
    //public void arrangeXxx() {
    //    ...
    //}
    /**
     * Arrange the query for selecting service members.
     * o starts 'S'
     * o status 'Formalized'
     * o exists the special product
     */
    public void arrangeServiceMember() {
        final Integer specialProductId = 3;
        setMemberName_PrefixSearch("S");
        setMemberStatusCode_Equal_正式会員();
        existsPurchaseList(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                subCB.query().setProductId_Equal(specialProductId);
            }
        });
    }
}
