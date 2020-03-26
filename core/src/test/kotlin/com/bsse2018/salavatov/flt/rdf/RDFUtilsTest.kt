package com.bsse2018.salavatov.flt.rdf

import org.eclipse.rdf4j.model.IRI
import org.eclipse.rdf4j.model.Literal
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.rio.RDFFormat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.FileNotFoundException


internal class RDFUtilsTest {
    private fun String.asResource() = RDFUtilsTest::class.java.classLoader.getResource(this)

    @Nested
    @DisplayName("RDF Loading")
    inner class RDFLoading {
        @Test
        fun `artists sample`() {
            val resource = "test-res/sample-RDFs/example-data-artists.ttl".asResource()
                ?: throw FileNotFoundException("test resource file not found")
            val model = loadRDF(
                ByteArrayInputStream(resource.readBytes()),
                RDFFormat.TURTLE
            )

            val vf = SimpleValueFactory.getInstance()
            val vanGogh: IRI = vf.createIRI("http://example.org/VanGogh")
            val aboutVanGogh = model.filter(vanGogh, null, null)
            var foundFirstName = false
            var foundLastName = false
            var foundSunflowers = false

            aboutVanGogh.forEach {
                foundFirstName =
                    foundFirstName || ((it.predicate as IRI).stringValue() == "http://xmlns.com/foaf/0.1/firstName")
                foundLastName = foundLastName || ((it.predicate as IRI).stringValue() == "http://xmlns.com/foaf/0.1/surname")
                foundSunflowers = foundSunflowers || (it.`object`.stringValue() == "http://example.org/sunflowers")
            }
            assert(foundFirstName)
            assert(foundLastName)
            assert(foundSunflowers)
        }

        @Test
        fun `graph sample`() {
            val resource = "test-res/sample-RDFs/sample-graph.ttl".asResource()
                ?: throw FileNotFoundException("test resource file not found")
            val model = loadRDF(
                ByteArrayInputStream(resource.readBytes()),
                RDFFormat.TURTLE
            )

            val graph = mutableMapOf<String, MutableSet<String>>()
            var edges = 0

            model.forEach {
                val src = (it.subject as IRI).localName
                val edge = (it.predicate as IRI).localName
                val dest = (it.`object` as IRI).localName
                assert(edge == "edge")
                graph.getOrPut(src, { mutableSetOf<String>() }).add(dest)
                edges++
            }
            assert(edges == 6)
            for (src in ('a'..'d')) {
                for (dst in (src.inc()..'d')) {
                    assert(graph[src.toString()]!!.contains(dst.toString()))
                }
            }
        }
    }
}