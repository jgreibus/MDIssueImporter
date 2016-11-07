package lt.jgreibus.magicdraw.redmine.plugin.actions;

import com.nomagic.magicdraw.ui.browser.Node;
import com.nomagic.magicdraw.ui.browser.Tree;
import com.nomagic.magicdraw.ui.browser.actions.DefaultBrowserAction;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ImportProblemsInBrowser extends DefaultBrowserAction {
    public ImportProblemsInBrowser() {
        super("", "Import Problems", null, null);
    }

    public void actionPerformed(ActionEvent e) {
        Tree tree = getTree();
        Node node = tree.getSelectedNode();

        Object userObject = node.getUserObject();
        if (userObject instanceof Package)
            JOptionPane.showMessageDialog(MDDialogParentProvider.getProvider().getDialogParent(), ((Package) userObject).getHumanName());

    }
}
