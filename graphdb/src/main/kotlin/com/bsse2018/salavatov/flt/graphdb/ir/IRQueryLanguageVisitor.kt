package com.bsse2018.salavatov.flt.graphdb.ir

import QueryLanguageVisitor
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

class UnexpectedParseResultException(desc: String) : Exception(desc)

object IRQueryLanguageVisitor : QueryLanguageVisitor<IR> {
    override fun visitScript(ctx: QueryLanguageParser.ScriptContext): IRScript {
        val statements = ctx.statement().map {
            visitStatement(it)
        }
        return IRScript(statements)
    }

    override fun visitStatement(ctx: QueryLanguageParser.StatementContext): IRStatement {
        ctx.stmt_connect()?.let {
            return visitStmt_connect(it)
        }
        ctx.stmt_list()?.let {
            return visitStmt_list(it)
        }
        ctx.stmt_pattern_decl()?.let {
            return visitStmt_pattern_decl(it)
        }
        ctx.stmt_select()?.let {
            return visitStmt_select(it)
        }
        throw UnexpectedParseResultException("$ctx doesn't look like Statement")
    }

    override fun visitStmt_connect(ctx: QueryLanguageParser.Stmt_connectContext): IRStatementConnect {
        return IRStatementConnect(ctx.STRING_DESC().text)
    }

    override fun visitStmt_list(ctx: QueryLanguageParser.Stmt_listContext): IRStatementList {
        return IRStatementList
    }

    override fun visitStmt_select(ctx: QueryLanguageParser.Stmt_selectContext): IRStatementSelect {
        return IRStatementSelect(
            visitObject_expr(ctx.object_expr()),
            visitFrom_expr(ctx.from_expr()),
            visitWhere_expr(ctx.where_expr())
        )
    }

    override fun visitStmt_pattern_decl(ctx: QueryLanguageParser.Stmt_pattern_declContext): IRStatementPatternDeclaration {
        return IRStatementPatternDeclaration(
            ctx.NONTERMINAL().text,
            visitPattern(ctx.pattern())
        )
    }

    override fun visitObject_expr(ctx: QueryLanguageParser.Object_exprContext): IRObjectExpression {
        ctx.vertices_expr()?.let {
            return visitVertices_expr(it)
        }
        ctx.count_expr()?.let {
            return visitCount_expr(it)
        }
        ctx.exists_expr()?.let {
            return visitExists_expr(it)
        }
        throw UnexpectedParseResultException("$ctx doesn't look like ObjectExpr")
    }

    override fun visitFrom_expr(ctx: QueryLanguageParser.From_exprContext): IRFromExpression {
        return IRFromExpression(ctx.STRING_DESC().text)
    }

    override fun visitWhere_expr(ctx: QueryLanguageParser.Where_exprContext): IRWhereExpression {
        return IRWhereExpression(visitPath_desc(ctx.path_desc()))
    }

    override fun visitVertices_expr(ctx: QueryLanguageParser.Vertices_exprContext): IRVerticesExpression {
        return IRVerticesExpression(visitVertices_desc(ctx.vertices_desc()))
    }

    override fun visitCount_expr(ctx: QueryLanguageParser.Count_exprContext): IRCountExpression {
        return IRCountExpression(visitVertices_desc(ctx.vertices_desc()))
    }

    override fun visitExists_expr(ctx: QueryLanguageParser.Exists_exprContext): IRExistsExpression {
        return IRExistsExpression(visitVertices_desc(ctx.vertices_desc()))
    }

    override fun visitVertices_desc(ctx: QueryLanguageParser.Vertices_descContext): IRVerticesDescription {
        ctx.pair_vertices_desc()?.let {
            return visitPair_vertices_desc(it)
        }
        ctx.single_vertex_desc()?.let {
            return visitSingle_vertex_desc(it)
        }
        throw UnexpectedParseResultException("$ctx doesn't look like VerticesDescription")
    }

    override fun visitPair_vertices_desc(ctx: QueryLanguageParser.Pair_vertices_descContext): IRPairVerticesDescription {
        return IRPairVerticesDescription(visitVertex_name(ctx.vertex_name(0)), visitVertex_name(ctx.vertex_name(1)))
    }

    override fun visitSingle_vertex_desc(ctx: QueryLanguageParser.Single_vertex_descContext): IRSingleVertexDescription {
        return IRSingleVertexDescription(visitVertex_name(ctx.vertex_name()))
    }

    override fun visitPath_desc(ctx: QueryLanguageParser.Path_descContext): IRPathDescription {
        return IRPathDescription(
            visitVertex_cond(ctx.vertex_cond(0)),
            visitPattern(ctx.pattern()),
            visitVertex_cond(ctx.vertex_cond(1))
        )
    }

    override fun visitVertex_cond(ctx: QueryLanguageParser.Vertex_condContext): IRVertexCondition {
        ctx.INTEGER()?.let { id ->
            return IRVertexConditionWithId(visitVertex_name(ctx.vertex_name()), id.text.toInt())
        }
        return IRVertexConditionPure(visitVertex_name(ctx.vertex_name()))
    }

    override fun visitTerminal(ctx: QueryLanguageParser.TerminalContext): IRTerminal {
        ctx.epsilon()?.let {
            return IREpsilon
        }
        return IRTerminalPure(ctx.LOWERCASE_WORD().text)
    }

    override fun visitEpsilon(ctx: QueryLanguageParser.EpsilonContext): IREpsilon {
        return IREpsilon
    }

    @Deprecated("should not call")
    override fun visit(tree: ParseTree): IR {
        throw NotImplementedError()
    }

    @Deprecated("should not call")
    override fun visitErrorNode(node: ErrorNode): IR {
        throw NotImplementedError()
    }

    @Deprecated("should not call")
    override fun visitTerminal(node: TerminalNode): IR {
        throw NotImplementedError()
    }

    override fun visitPattern(ctx: QueryLanguageParser.PatternContext): IRPattern {
        fun getModifier() =
            ctx.PTRN_STAR()?.let { IRPatternStar } ?: ctx.PTRN_PLUS()?.let { IRPatternPlus } ?: ctx.PTRN_OPTION()
                ?.let { IRPatternOptional }
        if (ctx.PTRN_ALT() != null) {
            return IRPatternAlternative(visitPattern(ctx.pattern(0)), visitPattern(ctx.pattern(1)))
        }
        if (ctx.pattern().size == 2) {
            return IRPatternConcat(visitPattern(ctx.pattern(0)), visitPattern(ctx.pattern(1)))
        }
        if (ctx.pattern().size == 1) {
            return IRPatternParenthesis(visitPattern(ctx.pattern(0)), getModifier())
        }
        if (ctx.NONTERMINAL() != null) {
            return IRPatternNonterminal(ctx.NONTERMINAL().text, getModifier())
        }
        if (ctx.terminal() != null) {
            return IRPatternTerminal(visitTerminal(ctx.terminal()), getModifier())
        }
        throw UnexpectedParseResultException("$ctx doesn't look like Pattern")
    }

    override fun visitVertex_name(ctx: QueryLanguageParser.Vertex_nameContext): IRVertexName {
        return IRVertexName(ctx.LOWERCASE_WORD().text)
    }

    @Deprecated("should not call")
    override fun visitChildren(node: RuleNode): IR {
        throw NotImplementedError()
    }

}