
# ========================================================================================
#                                                                                 Overview
#                                                                                 ========
The quick stage for DBFlute (and Seasar).
You can get the environment to execute DBFlute quickly.

src/test/java の org.dbflute.quickstage.dbflute.thematic 配下の、
PrototypeOfDemoTest をコピーして、DemoTest を作成してみましょう。
test_demo() でJUnit実行すれば、すぐに ConditionBean をテストできます。
(Quick JUnit であれば ctrl + 0 で実行できます)


# ========================================================================================
#                                                                              Environment
#                                                                              ===========
# ----------------------------------------------------------
#                                         Compile & Database
#                                         ------------------
M2EなどのMaven管理ができる環境が整っていればビルドすることができます。
また、組み込みDB「H2」を利用しているため、そのままテストを実行してExampleを試すことが可能です。

# ----------------------------------------------------------
#                                                 Quick Test
#                                                 ----------
(Overviewで説明した通り)

# ----------------------------------------------------------
#                                               DBFluteの構成
#                                               ------------
DBFluteは、大きく三つのもので構成されています。
  o DBFluteモジュール：自動生成のためのDBFlute本体モジュール(mydbflute配下)
  o DBFluteクライアント：プロジェクト固有の情報を管理するクライアント(dbflute_xxx)
  o DBFluteランタイム：実行時のランタイムライブラリ(dbflute-runtime-x-x-x.jar)

DBFluteクライアントがDBFluteモジュールを使ってDBFluteタスクを実行してクラスを自動生成し、
自動生成されたクラスは、DBFluteランタイムを使ってDBアクセスを行います。

# ----------------------------------------------------------
#                                            DBFluteランタイム
#                                            ---------------
DBFluteランタイム(dbflute-runtime-x-x-x.jar)の依存ライブラリは以下の通りです。
  o commons-logging-1.1.1

Maven2を使った場合のDBFluteランタイムのdependency定義は以下の通りです。

<dependency>
	<groupId>org.seasar.dbflute</groupId>
	<artifactId>dbflute-runtime</artifactId>
	<version>1.0.0</version>
</dependency>

# ----------------------------------------------------------
#                                              DBFlute Dicon
#                                              -------------
src/main/resources配下に「dbflute.dicon」があります。
DBFluteのクラス自動生成時に一緒に出力され、これを手動で修正することはありません。


# ========================================================================================
#                                                                              Information
#                                                                              ===========
# ----------------------------------------------------------
#                                              Example Class
#                                              -------------
src/test/java の org.dbflute.quickstage.dbflute.howto 配下：

  o BehaviorBasicTest
  o BehaviorMiddleTest
  o BehaviorPlatinumTest
  o ConditionBeanBasicTest
  o ConditionBeanMiddleTest
  o ConditionBeanPlatinumTest

# ----------------------------------------------------------
#                                                Code Format
#                                                -----------
DBFlute標準のフォーマット定義を利用しています。
Eclipseのデフォルトから主に二つの項目を変更しています。
　o コメントはフォーマットしない
　o マージンを「80」から「120」に
こちらは好みですが、コメントはフォーマットされると困ることが多いため、
OFFにしております。（タグコメントの空白が除去されたりなど）


# ========================================================================================
#                                                                            Demo Resource
#                                                                            =============
-- alter table MEMBER alter column MEMBER_ACCOUNT VARCHAR(80) NULL; 

