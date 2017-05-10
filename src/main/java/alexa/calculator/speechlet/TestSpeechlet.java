package alexa.calculator.speechlet;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

import alexa.calculator.logic.interpreter.LanguageToProcessedScriptMapper;
import alexa.calculator.logic.interpreter.SimplePriorityRuleBasedMapper;
import alexa.calculator.logic.interpreter.commands.InterpreterCommand;

public class TestSpeechlet implements Speechlet {
	
	private static final Logger log = LoggerFactory.getLogger(TestSpeechlet.class);
	
	private static final LanguageToProcessedScriptMapper mapper = new ClassPathXmlApplicationContext("Beans.xml").getBean(SimplePriorityRuleBasedMapper.class);

	@Override
	public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
		
		log.info("onLaunch requestId={}, sessionId={}",
				request.getRequestId(),
				session.getSessionId());
		
		Intent intent = request.getIntent();
		if(intent != null) {
			
			if(intent.getName().equals("TestIntent")) {
				
				String rawInput = intent.getSlot("Input").getValue();
				
				if(rawInput != null && !rawInput.isEmpty()) {
					
					Collection<List<InterpreterCommand>> output = mapper.map(rawInput);
					
					if(output.iterator().hasNext()) {
						
						String testResponse = "Commands detected ,";
						
						for(InterpreterCommand command : output.iterator().next())
						{
							testResponse += " Command "+command.getType()+" "+command.getValue()+" ,";
						}
						
						
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
