package alexa.calculator.speechlet;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class CalculatorSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
	
	private static final Set<String> supportedApplicationIds = new HashSet<String>();

    static {
    	//should work?
        //String appId = System.getenv("APP_ID");
        supportedApplicationIds.add("amzn1.ask.skill.43eb2d2b-f695-42c7-b0a6-2dc05a75dd93");
    }

	public CalculatorSpeechletRequestStreamHandler() {
		super(new TestSpeechlet(), supportedApplicationIds);
		// TODO Auto-generated constructor stub
	}

}
