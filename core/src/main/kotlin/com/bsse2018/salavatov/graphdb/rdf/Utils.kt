package com.bsse2018.salavatov.graphdb.rdf

import org.eclipse.rdf4j.model.Model
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio
import java.io.FileInputStream
import java.io.InputStream


// just a sample method for testing
fun loadRDF(input: InputStream, format: RDFFormat): Model {
    return Rio.parse(input, "", format)
}