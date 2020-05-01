package antlr;// Generated from QueryLanguage.g4 by ANTLR 4.8

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QueryLanguageParser}.
 */
public interface QueryLanguageListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#script}.
     *
     * @param ctx the parse tree
     */
    void enterScript(QueryLanguageParser.ScriptContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#script}.
     *
     * @param ctx the parse tree
     */
    void exitScript(QueryLanguageParser.ScriptContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterStatement(QueryLanguageParser.StatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitStatement(QueryLanguageParser.StatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#stmt_connect}.
     *
     * @param ctx the parse tree
     */
    void enterStmt_connect(QueryLanguageParser.Stmt_connectContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#stmt_connect}.
     *
     * @param ctx the parse tree
     */
    void exitStmt_connect(QueryLanguageParser.Stmt_connectContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#stmt_list}.
     *
     * @param ctx the parse tree
     */
    void enterStmt_list(QueryLanguageParser.Stmt_listContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#stmt_list}.
     *
     * @param ctx the parse tree
     */
    void exitStmt_list(QueryLanguageParser.Stmt_listContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#stmt_select}.
     *
     * @param ctx the parse tree
     */
    void enterStmt_select(QueryLanguageParser.Stmt_selectContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#stmt_select}.
     *
     * @param ctx the parse tree
     */
    void exitStmt_select(QueryLanguageParser.Stmt_selectContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#stmt_pattern_decl}.
     *
     * @param ctx the parse tree
     */
    void enterStmt_pattern_decl(QueryLanguageParser.Stmt_pattern_declContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#stmt_pattern_decl}.
     *
     * @param ctx the parse tree
     */
    void exitStmt_pattern_decl(QueryLanguageParser.Stmt_pattern_declContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#object_expr}.
     *
     * @param ctx the parse tree
     */
    void enterObject_expr(QueryLanguageParser.Object_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#object_expr}.
     *
     * @param ctx the parse tree
     */
    void exitObject_expr(QueryLanguageParser.Object_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#from_expr}.
     *
     * @param ctx the parse tree
     */
    void enterFrom_expr(QueryLanguageParser.From_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#from_expr}.
     *
     * @param ctx the parse tree
     */
    void exitFrom_expr(QueryLanguageParser.From_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#where_expr}.
     *
     * @param ctx the parse tree
     */
    void enterWhere_expr(QueryLanguageParser.Where_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#where_expr}.
     *
     * @param ctx the parse tree
     */
    void exitWhere_expr(QueryLanguageParser.Where_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#vertices_expr}.
     *
     * @param ctx the parse tree
     */
    void enterVertices_expr(QueryLanguageParser.Vertices_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#vertices_expr}.
     *
     * @param ctx the parse tree
     */
    void exitVertices_expr(QueryLanguageParser.Vertices_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#count_expr}.
     *
     * @param ctx the parse tree
     */
    void enterCount_expr(QueryLanguageParser.Count_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#count_expr}.
     *
     * @param ctx the parse tree
     */
    void exitCount_expr(QueryLanguageParser.Count_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#exists_expr}.
     *
     * @param ctx the parse tree
     */
    void enterExists_expr(QueryLanguageParser.Exists_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#exists_expr}.
     *
     * @param ctx the parse tree
     */
    void exitExists_expr(QueryLanguageParser.Exists_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#vertices_desc}.
     *
     * @param ctx the parse tree
     */
    void enterVertices_desc(QueryLanguageParser.Vertices_descContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#vertices_desc}.
     *
     * @param ctx the parse tree
     */
    void exitVertices_desc(QueryLanguageParser.Vertices_descContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#pair_vertices_desc}.
     *
     * @param ctx the parse tree
     */
    void enterPair_vertices_desc(QueryLanguageParser.Pair_vertices_descContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#pair_vertices_desc}.
     *
     * @param ctx the parse tree
     */
    void exitPair_vertices_desc(QueryLanguageParser.Pair_vertices_descContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#single_vertex_desc}.
     *
     * @param ctx the parse tree
     */
    void enterSingle_vertex_desc(QueryLanguageParser.Single_vertex_descContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#single_vertex_desc}.
     *
     * @param ctx the parse tree
     */
    void exitSingle_vertex_desc(QueryLanguageParser.Single_vertex_descContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#path_desc}.
     *
     * @param ctx the parse tree
     */
    void enterPath_desc(QueryLanguageParser.Path_descContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#path_desc}.
     *
     * @param ctx the parse tree
     */
    void exitPath_desc(QueryLanguageParser.Path_descContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#vertex_cond}.
     *
     * @param ctx the parse tree
     */
    void enterVertex_cond(QueryLanguageParser.Vertex_condContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#vertex_cond}.
     *
     * @param ctx the parse tree
     */
    void exitVertex_cond(QueryLanguageParser.Vertex_condContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#pattern}.
     *
     * @param ctx the parse tree
     */
    void enterPattern(QueryLanguageParser.PatternContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#pattern}.
     *
     * @param ctx the parse tree
     */
    void exitPattern(QueryLanguageParser.PatternContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#terminal}.
     *
     * @param ctx the parse tree
     */
    void enterTerminal(QueryLanguageParser.TerminalContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#terminal}.
     *
     * @param ctx the parse tree
     */
    void exitTerminal(QueryLanguageParser.TerminalContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#epsilon}.
     *
     * @param ctx the parse tree
     */
    void enterEpsilon(QueryLanguageParser.EpsilonContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#epsilon}.
     *
     * @param ctx the parse tree
     */
    void exitEpsilon(QueryLanguageParser.EpsilonContext ctx);

    /**
     * Enter a parse tree produced by {@link QueryLanguageParser#vertex_name}.
     *
     * @param ctx the parse tree
     */
    void enterVertex_name(QueryLanguageParser.Vertex_nameContext ctx);

    /**
     * Exit a parse tree produced by {@link QueryLanguageParser#vertex_name}.
     *
     * @param ctx the parse tree
     */
    void exitVertex_name(QueryLanguageParser.Vertex_nameContext ctx);
}