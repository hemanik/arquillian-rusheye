package org.jboss.lupic.test.parser;

import java.util.Iterator;

import org.dom4j.Document;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import static org.jboss.lupic.test.parser.VisualSuiteDefinitions.*;

@SuppressWarnings("unchecked")
public class VisualSuiteStub {

	Document document = DocumentHelper.createDocument();

	Element visualSuite = document.addElement(VISUAL_SUITE).addNamespace("xsi",
			"http://www.w3.org/2001/XMLSchema-instance").addAttribute(
			"xsi:schemaLocation",
			LUPIC_NS.getURI() + " src/main/resources/visual-suite.xsd");

	Element globalConfiguration = visualSuite.addElement(GLOBAL_CONFIGURATION);

	Element imageRetriever = globalConfiguration.addElement(IMAGE_RETRIEVER)
			.addAttribute("class", "org.jboss.lupic.retriever.FileRetriever");
	Element maskRetriever = globalConfiguration.addElement(MASK_RETRIEVER)
			.addAttribute("class", "org.jboss.lupic.retriever.FileRetriever");
	Element perception = globalConfiguration.addElement(PERCEPTION);

	Element defaultTest = visualSuite.addElement(TEST).addAttribute("name",
			"default-test");

	Element defaultPattern = defaultTest.addElement(PATTERN).addAttribute(
			"name", "default-test-pattern");

	{
		Iterator<Element> iterator = visualSuite.elementIterator(TEST);
		if (iterator.next() != defaultTest) {
			throw new IllegalStateException();
		}
		if (iterator.hasNext()) {
			visualSuite.remove(defaultTest);
		}
	}

}
