package j.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("bookAuthorValidator")
public class BookNameValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		String author = (String) value;
		
		if (author.length() < 4) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Autor musi składać się z conajmniej 4 znaki");
			message.setSummary("Autor musi składać się z conajmniej 4 znaki");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
}
