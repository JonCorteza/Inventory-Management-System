package inventorymanagementsystem.ui;

import inventorymanagementsystem.model.Supplier;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Add/Update dialog for suppliers - mirrors {@link ProductDialog}. Pass an
 * existing Supplier to pre-fill it in edit mode, or null to add a new one.
 * Only the "Add" path is currently wired up in the UI (via the "+ ADD
 * SUPPLIER" button on the Report Generation screen); the edit mode is here
 * ready to use if an Update Supplier button gets added later.
 */
public class SupplierDialog extends JDialog {

    private final JTextField nameField = new JTextField();
    private final JTextField contactField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField addressField = new JTextField();

    private final Supplier editing;
    private Supplier result;
    private boolean saved = false;

    public SupplierDialog(Frame owner, Supplier existing) {
        super(owner, existing == null ? "Add Supplier" : "Update Supplier", true);
        this.editing = existing;
        buildUI();
        if (existing != null) {
            populateFrom(existing);
        }
        setSize(420, 320);
        setLocationRelativeTo(owner);
    }

    private void buildUI() {
        getContentPane().setBackground(UITheme.BG_APP);
        setLayout(new BorderLayout(0, 12));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(20, 20, 10, 20));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        addField(form, gc, 0, "Supplier name", nameField);
        addField(form, gc, 1, "Contact person", contactField);
        addField(form, gc, 2, "Phone", phoneField);
        addField(form, gc, 3, "Address", addressField);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttons.setOpaque(false);
        JButton cancel = new JButton("Cancel");
        UITheme.styleSecondaryButton(cancel);
        cancel.addActionListener(e -> dispose());

        JButton save = new JButton(editing == null ? "Add" : "Save");
        UITheme.stylePrimaryButton(save);
        save.addActionListener(e -> onSave());

        buttons.add(cancel);
        buttons.add(save);
        add(buttons, BorderLayout.SOUTH);
    }

    private void addField(JPanel form, GridBagConstraints gc, int row, String label, JTextField field) {
        gc.gridx = 0;
        gc.gridy = row;
        gc.weightx = 0;
        JLabel l = new JLabel(label);
        l.setForeground(UITheme.TEXT_MUTED);
        l.setFont(UITheme.FONT_BODY);
        form.add(l, gc);

        gc.gridx = 1;
        gc.weightx = 1;
        UITheme.styleTextField(field);
        form.add(field, gc);
    }

    private void populateFrom(Supplier s) {
        nameField.setText(s.getSupplierName());
        contactField.setText(s.getContactPerson());
        phoneField.setText(s.getPhone());
        addressField.setText(s.getAddress());
    }

    private void onSave() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Supplier name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        result = editing == null ? new Supplier() : editing;
        result.setSupplierName(nameField.getText().trim());
        result.setContactPerson(contactField.getText().trim());
        result.setPhone(phoneField.getText().trim());
        result.setAddress(addressField.getText().trim());

        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public Supplier getResult() {
        return result;
    }
}
