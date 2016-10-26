package elementSelectionManager;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.magicdraw.ui.dialogs.SelectElementInfo;
import com.nomagic.magicdraw.ui.dialogs.SelectElementTypes;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlgFactory;
import com.nomagic.magicdraw.ui.dialogs.selection.TypeFilter;
import com.nomagic.magicdraw.ui.dialogs.selection.TypeFilterImpl;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.CallBehaviorAction;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class elementSelectionManager {
    public static ElementSelectionDlg createElementSelectionDialog(List types) {
        // Only properties and and their subtypes are offered to select.
        SelectElementTypes selectElementTypes = new SelectElementTypes(types, types, null, types);

        // Available properties are filtered so that only the ones which start with 'p' are selected.
       // final Collection<Property> candidates = getSelectionCandidates("p");

        TypeFilter selectableFilter = new TypeFilterImpl(selectElementTypes.select) {
            @Override
            public boolean accept(@Nonnull BaseElement baseElement, boolean checkType) {
                return super.accept(baseElement, checkType);// && candidates.contains(baseElement);
            }
        };

        TypeFilter visibleFilter = new TypeFilterImpl(selectElementTypes.display) {
            @Override
            public boolean accept(@Nonnull BaseElement baseElement, boolean checkType) {
                return super.accept(baseElement, checkType);// && candidates.contains(baseElement);
            }
        };

        Frame dialogParent = MDDialogParentProvider.getProvider().getDialogParent();
        ElementSelectionDlg selectionDlg = ElementSelectionDlgFactory.create(dialogParent, "Select properties which start with 'p'", null);

        SelectElementInfo selectElementInfo = new SelectElementInfo(true, false, null, true);
        // Gets elements which are initially selected in the dialog.
        List<CallBehaviorAction> initialSelection = getInitialSelection();
        //List<Class> initialSelection = new Class[]{CallBehaviorAction.class};
        ElementSelectionDlgFactory.initMultiple(selectionDlg, selectElementInfo, visibleFilter, selectableFilter, selectElementTypes.usedAsTypes,
                selectElementTypes.create, initialSelection);

        return selectionDlg;
    }

    private static List<CallBehaviorAction> getInitialSelection() {
        final List<CallBehaviorAction> actions = new ArrayList<>();

        final Project project = Application.getInstance().getProject();
        if(project != null) {
            final Collection<CallBehaviorAction> candidates = Finder.byTypeRecursively().find(project, new Class[]{CallBehaviorAction.class}, false);
            for (CallBehaviorAction action : candidates) {
                if (action.getName().startsWith("a")) {
                    actions.add(action);
                }
            }
        }
        return actions;
    }
}
