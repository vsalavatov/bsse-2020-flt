package antlr;// Generated from QueryLanguage.g4 by ANTLR 4.8

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link QueryLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface QueryLanguageVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#script}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitScript(QueryLanguageParser.ScriptContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStatement(QueryLanguageParser.StatementContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#stmt_connect}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStmt_connect(QueryLanguageParser.Stmt_connectContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#stmt_list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStmt_list(QueryLanguageParser.Stmt_listContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#stmt_select}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStmt_select(QueryLanguageParser.Stmt_selectContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#stmt_pattern_decl}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStmt_pattern_decl(QueryLanguageParser.Stmt_pattern_declContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#object_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitObject_expr(QueryLanguageParser.Object_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#from_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFrom_expr(QueryLanguageParser.From_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#where_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWhere_expr(QueryLanguageParser.Where_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#vertices_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitVertices_expr(QueryLanguageParser.Vertices_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#count_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCount_expr(QueryLanguageParser.Count_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#exists_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExists_expr(QueryLanguageParser.Exists_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#vertices_desc}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitVertices_desc(QueryLanguageParser.Vertices_descContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#pair_vertices_desc}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPair_vertices_desc(QueryLanguageParser.Pair_vertices_descContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#single_vertex_desc}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSingle_vertex_desc(QueryLanguageParser.Single_vertex_descContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#path_desc}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPath_desc(QueryLanguageParser.Path_descContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#vertex_cond}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitVertex_cond(QueryLanguageParser.Vertex_condContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#pattern}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPattern(QueryLanguageParser.PatternContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#terminal}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTerminal(QueryLanguageParser.TerminalContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#epsilon}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEpsilon(QueryLanguageParser.EpsilonContext ctx);

    /**
     * Visit a parse tree produced by {@link QueryLanguageParser#vertex_name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitVertex_name(QueryLanguageParser.Vertex_nameContext ctx);
}