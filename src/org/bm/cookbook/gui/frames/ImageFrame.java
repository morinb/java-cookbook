package org.bm.cookbook.gui.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.bm.cookbook.db.model.Image;
import org.bm.cookbook.db.model.Model;
import org.bm.cookbook.gui.Messages;
import org.bm.cookbook.gui.utils.ImagePanel;
import org.bm.cookbook.gui.utils.ImagePreviewPanel;
import org.bm.cookbook.utils.CBUtils;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class ImageFrame extends CookbookInternalFrame {
	private Image current;
	private JButton add;
	private JButton delete;
	private JButton update;

	private JTextField path;
	private JTextField name;
	private JButton search;

	private ImagePanel image;

	private JList<Image> imageList;

	public ImageFrame() {}

	@Override
	protected void createGui() {
		this.setTitle(Messages.getString("CookbookFrame.itemImage")); //$NON-NLS-1$
		FormLayout layout = new FormLayout("p, 4dlu, p, 4dlu, p", "p, 2dlu, p, 2dlu, p, 2dlu, f:p:g"); //$NON-NLS-1$ //$NON-NLS-2$

		add = new JButton(Messages.getString("ImageFrame.buttonAdd")); //$NON-NLS-1$
		
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Collection<Component> badComponents = new ArrayList<Component>();

				if (name.getText().isEmpty()) {
					badComponents.add(name);
				}

				if (path.getText().isEmpty()) {
					badComponents.add(path);
				}

				if (!badComponents.isEmpty()) {
					JOptionPane.showMessageDialog(
							ImageFrame.this,
							Messages.getString("UnitFrame.notEmptyMessage"), Messages.getString("UnitFrame.errorWarning"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$

					for (Component c : badComponents) {
						c.setBackground(ERROR_BACKGROUND_COLOR);
					}

					return;
				}

				Image i = new Image();
				i.setName(name.getText());
				i.setImagePath(path.getText());
				i.save();
				CookbookJXFrame.updateStatus(Messages.getString("ImageFrame.statusTextImageSaved")); //$NON-NLS-1$
				reload();
			}
		});
		delete = new JButton(Messages.getString("ImageFrame.buttonDelete")); //$NON-NLS-1$
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (current != null) {
					current.remove();
					current = null;
					CookbookJXFrame.updateStatus(Messages.getString("ImageFrame.statusTextImageDeleted")); //$NON-NLS-1$
					reload();
				}
			}
		});
		update = new JButton(Messages.getString("ImageFrame.buttonUpdate")); //$NON-NLS-1$
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (null != current) {
					Model.getEm().getTransaction().begin();
					current.setName(name.getText());
					current.setImagePath(path.getText());
					Model.getEm().getTransaction().commit();
					CookbookJXFrame.updateStatus(Messages.getString("ImageFrame.statusTextImageUpdated")); //$NON-NLS-1$
					reload();
				}
			}
		});

		name = new JTextField();
		PromptSupport.setPrompt(Messages.getString("ImageFrame.nameGhostText"), name); //$NON-NLS-1$

		path = new JTextField();
		path.setEditable(false);
		PromptSupport.setPrompt(Messages.getString("ImageFrame.pathGhostText"), path); //$NON-NLS-1$

		search = new JButton(Messages.getString("ImageFrame.buttonSearch")); //$NON-NLS-1$
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(new File(".")); //$NON-NLS-1$

				jfc.setFileFilter(new FileFilter() {

					@Override
					public String getDescription() {
						return Messages.getString("ImageFrame.imageFileDescription"); //$NON-NLS-1$
					}

					@Override
					public boolean accept(File f) {
						String ext = CBUtils.getExtension(f);
						boolean isExtension = CBUtils.isImageExtension(ext);
						return isExtension || f.isDirectory();
					}
				});
				ImagePreviewPanel preview = new ImagePreviewPanel();
				jfc.setAccessory(preview);
				jfc.addPropertyChangeListener(preview);

				if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(ImageFrame.this)) {
					File selectedFile = jfc.getSelectedFile();
					path.setText(selectedFile.getAbsolutePath());
					try {
						BufferedImage i = ImageIO.read(selectedFile);

						image.setImage(i);
					} catch (IOException e1) {
						JXErrorPane.showDialog(e1);
					}
				}
			}
		});
		image = new ImagePanel();

		imageList = new JList<Image>();
		
		imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		imageList.setPreferredSize(new Dimension(150, 200));
		imageList.setSize(new Dimension(150, 200));
		imageList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel c = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				
				if(value instanceof Image) {
					Image image = (Image)value;
					try {
						BufferedImage bi = ImageIO.read(new File(image.getImagePath()));
						int imageWidth = bi.getWidth();
						int imageHeight = bi.getHeight();
						
						double scale =(double)36/imageWidth; 
						int newHeight = (int) (scale * imageHeight);
						
						BufferedImage i = new BufferedImage(36, newHeight, BufferedImage.TYPE_INT_ARGB);
						
						
						Graphics g = i.getGraphics();
						g.drawImage(bi, 0, 0, 36, newHeight, this);
						
						ImageIcon ii = new ImageIcon(i);
						
						c.setIcon(ii);
					} catch (IOException e) {
						JXErrorPane.showFrame(e);
					}
				}
				
				return c;
			}
		});

		imageList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				current = imageList.getSelectedValue();
				if (null != current) {
					name.setText(current.getName());
					path.setText(current.getImagePath());
					BufferedImage i = null;
					try {
						i = ImageIO.read(new File(path.getText()));
					} catch (IOException e1) {
						JXErrorPane.showFrame(e1);
					}
					image.setImage(i);
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

		pb.add(path, CC.xyw(1, 3, 5));
		pb.add(name, CC.xyw(1, 5, 3));
		pb.add(search, CC.xy(5, 5));

		
		pb.add(image, CC.xy(1, 7));
		JScrollPane sp = new JScrollPane(imageList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(155, 205));
		pb.add(sp, CC.xyw(3, 7, 3));

		this.setContentPane(pb.build());
	}

	@Override
	protected void reload() {
		super.reload();

		DefaultListModel<Image> m = new DefaultListModel<>();

		Collection<Image> images = Image.findAll();
		for (Image image : images) {
			m.addElement(image);
		}

		imageList.setModel(m);

	}

	@Override
	protected void reset() {
		image.setImage(null);
		name.setText("");
		path.setText("");
	}
}
