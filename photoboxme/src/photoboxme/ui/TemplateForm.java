package photoboxme.ui;

import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.Form;

/**
 * Template for constructing forms. A form is a concept in LWUIT, which is
 * screen.
 * 
 * @author Sike Huang
 * 
 */
public abstract class TemplateForm extends Form implements ActionListener {
	private TemplateForm previsousForm;

	protected TemplateForm() {

	}

	/**
	 * Constructor where previous form is injected.
	 * 
	 * @param previsousForm
	 *            previous form
	 */
	protected TemplateForm(TemplateForm previsousForm) {
		this.previsousForm = previsousForm;
	}

	/**
	 * Build GUI components in the form.
	 */
	protected abstract void buildComponents();

	/**
	 * Attach listeners to the components.
	 */
	protected abstract void attachComponentListeners();

	/**
	 * Build soft buttons.
	 */
	protected abstract void buildSoftButtons();

	/**
	 * Set data model.
	 */
	protected abstract void setModel();

	/**
	 * Build the form and show it.
	 */
	public void handle() {
		buildComponents();
		attachComponentListeners();
		buildSoftButtons();
		setModel();
		show();
	}

	/**
	 * Navigate back to previous form.
	 */
	protected void goBack() {
		previsousForm.show();
	}
}
