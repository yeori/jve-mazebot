package andybot.view.renderer;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class MazeListRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -2506405746467622795L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		File file = (File) value;
		JLabel $this = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		$this.setText(file.getName());
		return $this;
	}
	
}