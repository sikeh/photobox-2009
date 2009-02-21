package photoboxme.ui;

import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.Form;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 20, 2009
 * Time: 9:26:44 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TemplateForm extends Form implements ActionListener {
    private TemplateForm previsousForm;

    protected TemplateForm() {

    }

    protected TemplateForm(TemplateForm previsousForm) {
        this.previsousForm = previsousForm;
    }

    protected abstract void buildComponents();

    protected abstract void attachComponentListeners();

    protected abstract void buildSoftButtons();

    protected abstract void setModel();

    public void handle() {
        buildComponents();
        attachComponentListeners();
        buildSoftButtons();
        setModel();
        show();
    }

    protected void goBack() {
        previsousForm.show();
    }
}
