package lt.jgreibus.magicdraw.redmine.plugin.actions;

import com.nomagic.magicdraw.ui.browser.Node;
import com.nomagic.magicdraw.ui.browser.Tree;
import com.nomagic.magicdraw.ui.browser.actions.DefaultBrowserAction;
import com.nomagic.ui.ScalableImageIcon;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import lt.jgreibus.magicdraw.redmine.tracker.manager.redmine.RedmineIssueManager;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ImportProblemsInBrowser extends DefaultBrowserAction {

    public ImportProblemsInBrowser() {
        super("", "Import Problems from Redmine", null, null);
        try {
            setSmallIcon(new ScalableImageIcon(ImageIO.read(ClassLoader.getSystemResource("icons/redmine.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        Tree tree = getTree();
        Node node = tree.getSelectedNode();

        Object userObject = node.getUserObject();
        if (userObject instanceof Package)
            RedmineIssueManager.GetRedmineIssues((Element) userObject);
    }

    @Override
    public void updateState() {
        setEnabled(getTree().getSelectedNode() != null);
    }
}
