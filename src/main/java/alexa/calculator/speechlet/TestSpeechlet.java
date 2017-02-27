package alexa.calculator.speechlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

public class TestSpeechlet implements Speechlet {
	
	private static final Logger log = LoggerFactory.getLogger(TestSpeechlet.class);

	@Override
	public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
		
		log.info("onLaunch requestId={}, sessionId={}",
				request.getRequestId(),
				session.getSessionId());
		
		Intent intent = request.getIntent();
		if(intent != null) {
			
			if(intent.getName().equals("TestIntent")) {
				String testResponse = "test succeeded";
				
				SimpleCard card = new SimpleCard();
				card.setTitle("Test");
				card.setContent(testResponse);
				
				PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
				speech.setText(testResponse);
				
				SpeechletResponse resp = SpeechletResponse.newTellResponse(speech, card);
				resp.setShouldEndSession(true);
				
				return resp;
				
			}
			
		}
		
		throw new SpeechletException("Invalid Intent");
	}

	@Override
	public SpeechletResponse onLaunch(LaunchRequest arg0, Session arg1) throws SpeechletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSessionEnded(SessionEndedRequest arg0, Session arg1) throws SpeechletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSessionStarted(SessionStartedRequest arg0, Session arg1) throws SpeechletException {
		// TODO Auto-generated method stub

	}

}
