package com.trivago.cluecumber.json.postprocessors;

import com.trivago.cluecumber.constants.MimeType;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Embedding;
import com.trivago.cluecumber.json.pojo.ResultMatch;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.logging.CluecumberLogger;
import com.trivago.cluecumber.properties.PropertyManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class ElementPostProcessorTest {
    private ElementPostProcessor elementPostProcessor;

    @Before
    public void setup() {
        PropertyManager propertyManager = mock(PropertyManager.class);
        FileIO fileIO = mock(FileIO.class);
        CluecumberLogger logger = new CluecumberLogger();
        elementPostProcessor = new ElementPostProcessor(propertyManager, fileIO, logger);
    }

    @Test
    public void postDesiralizeAddScenarioIndexBackgroundScenarioTest() {
        Element element = new Element();
        assertThat(element.getScenarioIndex(), is(0));
        elementPostProcessor.postDeserialize(element, null, null);
        assertThat(element.getScenarioIndex(), is(0));
    }

    @Test
    public void postDesiralizeAddScenarioIndexTest() {
        Element element = new Element();
        element.setType("scenario");
        assertThat(element.getScenarioIndex(), is(0));
        elementPostProcessor.postDeserialize(element, null, null);
        assertThat(element.getScenarioIndex(), is(1));
    }

    @Test
    public void postDeserializeTest() {
        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        List<Embedding> embeddings = new ArrayList<>();
        Embedding embedding = new Embedding();
        embedding.setMimeType(MimeType.PNG);
        embedding.setData("123");
        embeddings.add(embedding);
        step.setEmbeddings(embeddings);

        List<ResultMatch> before = new ArrayList<>();
        ResultMatch resultMatchBefore = new ResultMatch();
        before.add(resultMatchBefore);
        step.setBefore(before);

        List<ResultMatch> after = new ArrayList<>();
        ResultMatch resultMatchAfter = new ResultMatch();
        after.add(resultMatchAfter);
        step.setAfter(after);

        steps.add(step);
        element.setSteps(steps);

        List<ResultMatch> elementAfter = new ArrayList<>();
        ResultMatch resultMatchAfterElement = new ResultMatch();
        elementAfter.add(resultMatchAfter);
        element.setAfter(elementAfter);

        assertThat(embedding.getData(), is("123"));

        elementPostProcessor.postDeserialize(element, null, null);
        assertThat(embedding.getFilename(), is("attachment001.png"));
    }

    @Test
    public void postSerializeTest() {
        elementPostProcessor.postSerialize(null, null, null);
    }
}
