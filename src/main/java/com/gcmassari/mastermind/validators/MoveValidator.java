package com.gcmassari.mastermind.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.gcmassari.mastermind.data.GameParameters;
import com.gcmassari.mastermind.model.Color;
import com.gcmassari.mastermind.model.MoveForm;

@Component
public class MoveValidator  implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return MoveForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MoveForm moveForm = (MoveForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "move", "play.move.isEmpty");
		if (errors.hasErrors()) {
			return; // no more error messages
		}

		String move = moveForm.getMove().trim();
		if (move.length() != GameParameters.HOLES_NO) {
			errors.rejectValue(
				"move",
				"play.move.invalidLength",
				new Object[]{Integer.valueOf(GameParameters.HOLES_NO)},
				"Sequences should have " + GameParameters.HOLES_NO + " colors."
			);
		}
		List<Character> invalidChars = new ArrayList<Character>();
		for (int i = 0; i < move.length(); i++) {
			char ch = move.charAt(i);
			if (Color.valueOf(ch)== null && !invalidChars.contains(ch)) {
				invalidChars.add(ch);
			}
		}
		// if all chars are valid color codes: nothing else to do, return.
		if (invalidChars.isEmpty()) {
			return;
		}

		if (invalidChars.size()>1) {
			String invalidCharsList = "(" + StringUtils.join(invalidChars, ",") +")";
			errors.rejectValue("move",
				"play.move.multipleInvalidColors",
				new Object[]{invalidCharsList},
				invalidCharsList + "' are invalid color codes."
				);
		} else if (invalidChars.size()==1) {
			errors.rejectValue("move",
				"play.move.invalidColor",
				new Object[]{invalidChars.get(0)},
				"'" + invalidChars.get(0) + "' is not a valid color code."
				);
		}
	}
}
