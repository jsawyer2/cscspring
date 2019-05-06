import scala.io.Source
import java.io._
import scala.collection.mutable.LinkedHashMap
import edu.holycross.shot.scm._
import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.seqcomp._
import edu.furman.classics.citealign._
import java.util.Calendar


def loadLibrary(fp:String):CiteLibrary = {
	val library = CiteLibrary(Source.fromFile(fp).getLines.mkString("\n"),"#",",")
	library
}

def saveString(s:String, filePath:String = "html/", fileName:String = "temp.txt"):Unit = {
	val pw = new PrintWriter(new File(filePath + fileName))
	for (line <- s.lines){
		pw.append(line)
		pw.append("\n")
	}
	pw.close
}

val demoLib:String = "huckfinn.cex"
val urn:CtsUrn = CtsUrn("urn:cts:fuTexts:twain.finn.fu:")
lazy val lib = loadLibrary(demoLib)
lazy val tr = lib.textRepository.get
lazy val twainCorpus = tr.corpus

// Getting labels for a URN
val groupName:String = tr.catalog.groupName(urn)
val workTitle:String = tr.catalog.workTitle(urn)
val versionLabel:String = tr.catalog.versionLabel(urn)

// Chunk-by-citation
def chunkByCitation(c:Corpus, level:Int = 1):Vector[Corpus] = {
	// We need this, for this process only…
	import scala.collection.mutable.LinkedHashMap
	// we start with a Vector of CitableNodes from our corpus
	val v1:Vector[CitableNode] = c.nodes
	// We zipWithIndex to capture their sequence
	val v2:Vector[(CitableNode, Int)] = v1.zipWithIndex
	val v3:Vector[(CtsUrn, Vector[(CitableNode, Int)])] = {
		v2.groupBy( _._1.urn.collapsePassageTo(level) ).toVector
	}
	val v4 = LinkedHashMap(v3.sortBy(_._2.head._2): _*)
	val v5 = v4.mapValues(_ map (_._1)).toVector
	val corpusVec:Vector[Corpus] = v5.map( v => {
		val nodes:Vector[CitableNode] = v._2
		Corpus(nodes)
	})
	corpusVec
}

def writeParagraph(n:CitableNode):String = {
	"<p>" + n.text + "</p>"
}




// Write out each of 24 books
val htmlTop:String = s"""<!DOCTYPE html>
<html>
<head>
	<title>${workTitle}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" type="text/css" href="huckfinnstyle.css">
</head>

<body>

"""

val htmlBottom:String = """</body></html>"""

val bookChunks:Vector[Corpus] = chunkByCitation(twainCorpus, 1)

for ( bk <- bookChunks.zipWithIndex) {
	val bkNum:Int = bk._2 + 1
	val c:Corpus = bk._1

	val previousChapterLink:String = {
		if (bkNum == 1){
			""
		} else {
			 s"""<a href="book${bkNum - 1}.html">previous     </a>"""
		}
	}
	val nextChapterLink:String = {
		bkNum match {
			case n if (n == (bookChunks.size)) => { "" }
			case _ => { s"""<a href="book${bkNum + 1}.html">     next</a>""" }
		}
	}

	val htmlName:String = s"book${bkNum}.html"
	val chapterHeading:String = {
		s"<h2>Chapter ${bkNum}</h2>"
	}
	val textString:String = c.nodes.map( n => writeParagraph(n)).mkString("\n")
	val htmlString:String = htmlTop + previousChapterLink + nextChapterLink + chapterHeading + textString + previousChapterLink + nextChapterLink + htmlBottom
	saveString(htmlString, "html/", htmlName)
}
