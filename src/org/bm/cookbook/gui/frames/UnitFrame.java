package org.bm.cookbook.gui.frames;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.bm.cookbook.db.model.Model;
import org.bm.cookbook.db.model.Unit;
import org.bm.cookbook.gui.Messages;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class UnitFrame extends CookbookInternalFrame {
	private Unit current;
	private JTextField name;
	private JTextField abbreviation;

	private JButton add;
	private JButton delete;
	private JButton update;

	private JList<Unit> unitsList;

	public UnitFrame() {
		current = null;
	}

	@Override
	protected void createGui() {
		this.setTitle(Messages.getString("CookbookFrame.itemUnit")); //$NON-NLS-1$

		{
			FormLayout layout = new FormLayout("p:g, 4dlu, p:g, 4dlu, p:g", "p, 2dlu, p, 2dlu, fill:pref:grow"); //$NON-NLS-1$ //$NON-NLS-2$

			add = new JButton(Messages.getString("UnitFrame.buttonAdd")); //$NON-NLS-1$
			add.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Collection<Component> badComponents = new ArrayList<Component>();

					if (name.getText().isEmpty()) {
						badComponents.add(name);
					}
					if (abbreviation.getText().isEmpty()) {
						badComponents.add(abbreviation);
					}

					if (!badComponents.isEmpty()) {
						JOptionPane.showMessageDialog(
								UnitFrame.this,
								Messages.getString("UnitFrame.notEmptyMessage"), Messages.getString("UnitFrame.errorWarning"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$

						for (Component c : badComponents) {
							c.setBackground(ERROR_BACKGROUND_COLOR);
						}

						return;
					}

					Unit u = new Unit();
					u.setName(name.getText());
					u.setAbbreviation(abbreviation.getText());
					u.save();
					CookbookJXFrame.updateStatus(Messages.getString("UnitFrame.statusTextUnitSaved")); //$NON-NLS-1$
					reload();
				}
			});
			delete = new JButton(Messages.getString("UnitFrame.buttonDelete")); //$NON-NLS-1$
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (current != null) {
						current.remove();
						current = null;
						CookbookJXFrame.updateStatus(Messages.getString("UnitFrame.statusTextUnitDeleted")); //$NON-NLS-1$
						reload();
					}
				}
			});

			update = new JButton(Messages.getString("UnitFrame.buttonUpdate")); //$NON-NLS-1$

			update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (null != current) {
						Model.getEm().getTransaction().begin();
						current.setName(name.getText());
						current.setAbbreviation(abbreviation.getText());
						Model.getEm().getTransaction().commit();
						CookbookJXFrame.updateStatus(Messages.getString("UnitFrame.statusTextUnitUpdated")); //$NON-NLS-1$
						reload();
					}
				}
			});

			name = new JTextField();
			PromptSupport.setPrompt(Messages.getString("UnitFrame.ghostTextEnterName"), name); //$NON-NLS-1$
			abbreviation = new JTextField();
			PromptSupport.setPrompt(Messages.getString("UnitFrame.ghostTextEnterAbbreviation"), abbreviation); //$NON-NLS-1$
			unitsList = new JList<Unit>();

			unitsList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					current = unitsList.getSelectedValue();
					if (null != current) {
						name.setText(current.getName());
						abbreviation.setText(current.getAbbreviation());
					} else {
						reset();
					}
				}
			});
			reload();

			PanelBuilder pb = new PanelBuilder(layout);
			pb.border(Borders.DIALOG);
			
			pb.add(add, CC.xy(1, 1));
			pb.add(delete, CC.xy(3, 1));
			pb.add(update, CC.xy(5, 1));

			pb.add(name, CC.xyw(1, 3, 3));
			pb.add(abbreviation, CC.xy(5, 3));

			JScrollPane sp = new JScrollPane(unitsList);
			pb.add(sp, CC.xyw(1, 5, 5));

			this.setContentPane(pb.build());
		}

	}

	@Override
	protected void reload() {
		super.reload();

		DefaultListModel<Unit> m = new DefaultListModel<>();

		Collection<Unit> units = Unit.findAll();
		for (Unit unit : units) {
			m.addElement(unit);
		}

		unitsList.setModel(m);

	}

	@Override
	protected void reset() {
		name.setBackground(NORMAL_BACKGROUND_COLOR);
		abbreviation.setBackground(NORMAL_BACKGROUND_COLOR);
		name.setText(""); //$NON-NLS-1$
		abbreviation.setText(""); //$NON-NLS-1$
	}
}
