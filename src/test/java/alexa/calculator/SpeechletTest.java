package alexa.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Assert;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;

import alexa.calculator.speechlet.TestSpeechlet;

import com.amazon.speech.slu.Intent;



public class SpeechletTest {
	
	@Test
	public void testOnIntent() {
		
		Intent.Builder intentBuilder = Intent.builder();
		intentBuilder.withName("TestIntent");
		
		IntentRequest.Builder requestBuilder = IntentRequest.builder();
		requestBuilder.withIntent(intentBuilder.build());
		requestBuilder.withRequestId("EdwRequestId.9b791e98-d158-4c49-bd7b-83e1525eec0e");
		
		TestSpeechlet test = new TestSpeechlet();
		SpeechletResponse response;
		
		try {
			response = test.onIntent(requestBuilder.build(), Session.builder().withSessionId("SessionId.b839f813-c2a2-438e-b219-67220423fc66").build());
			
			PlainTextOutputSpeech out = (PlainTextOutputSpeech) response.getOutputSpeech();
			assertEquals("test succeeded",out.getText());
			
		} catch (SpeechletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
