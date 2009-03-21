package photoboxme.ui;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;

/**
 * Render for the list in the {@link HomeForm}, "fish eye" effect.
 * 
 * @author Sike Huang
 * 
 */
public class MenusRender extends Label implements ListCellRenderer {
	private Label lblItem = new Label("");
	private Label lblTip = new Label("");
	private Container selected = new Container(new BoxLayout(BoxLayout.Y_AXIS));

	private Image[] images;
	private String[] menuTips;

	public MenusRender(Image[] images, String[] menuTips) {
		this.images = images;
		this.menuTips = menuTips;

		lblItem.getStyle().setBgTransparency(0);
		selected.addComponent(lblItem);
		lblItem.setFocus(true);

		lblTip.getStyle().setBgTransparency(0);
		selected.addComponent(lblTip);
		lblTip.setFocus(true);
	}

	public Component getListCellRendererComponent(List list, Object value,
			int index, boolean isSelected) {
		String text = (String) value;

		if (isSelected) {
			lblItem.setIcon(images[index]);
			lblItem.setText(text);
			lblTip.setText(menuTips[index]);
			return selected;
		}

		setIcon(images[index]);
		setText(text);
		getStyle().setBgTransparency(0);
		setFocus(false);
		return this;
	}

	public Component getListFocusComponent(List list) {
		setIcon(null);
		setText("");
		setFocus(true);
		getStyle().setBgTransparency(100);
		return this;
	}

}
