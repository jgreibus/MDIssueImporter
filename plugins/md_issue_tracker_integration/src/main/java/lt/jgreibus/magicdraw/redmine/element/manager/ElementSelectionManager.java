package lt.jgreibus.magicdraw.redmine.element.manager;

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
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ElementSelectionManager {
    public static ElementSelectionDlg createElementSelectionDialog(List types) {

        SelectElementTypes selectElementTypes = new SelectElementTypes(types, types, null, types);
        TypeFilter selectableFilter = new TypeFilterImpl(selectElementTypes.select) {
            @Override
            public boolean accept(@Nonnull BaseElement baseElement, boolean checkType) {
                return super.accept(baseElement, checkType);
            }
        };

        TypeFilter visibleFilter = new TypeFilterImpl(selectElementTypes.display) {
            @Override
            public boolean accept(@Nonnull BaseElement baseElement, boolean checkType) {
                return super.accept(baseElement, checkType);
            }
        };

        Frame dialogParent = MDDialogParentProvider.getProvider().getDialogParent();
        ElementSelectionDlg selectionDlg = ElementSelectionDlgFactory.create(dialogParent, "Select element", null);

        SelectElementInfo selectElementInfo = new SelectElementInfo(true, false, null, true);
        ElementSelectionDlgFactory.initSingle(selectionDlg, selectElementInfo, visibleFilter, selectableFilter, null, null);

        return selectionDlg;
    }

    public static ElementSelectionDlg createStereotypedElementsSelectionDialog(List types, Stereotype stereotype) {

        SelectElementTypes selectElementTypes = new SelectElementTypes(types, types, stereotype);

        TypeFilter selectableFilter = new TypeFilterImpl(selectElementTypes.select) {
            @Override
            public boolean accept(@Nonnull BaseElement baseElement, boolean checkType) {
                return super.accept(baseElement, checkType);
            }
        };

        TypeFilter visibleFilter = new TypeFilterImpl(selectElementTypes.display) {
            @Override
            public boolean accept(@Nonnull BaseElement baseElement, boolean checkType) {
                return super.accept(baseElement, checkType);
            }
        };

        Frame dialogParent = MDDialogParentProvider.getProvider().getDialogParent();
        ElementSelectionDlg selectionDlg = ElementSelectionDlgFactory.create(dialogParent, "Select stereotyped elements", null);

        SelectElementInfo selectElementInfo = new SelectElementInfo(true, false, null, true);
        ElementSelectionDlgFactory.initMultiple(selectionDlg, selectElementTypes, selectElementInfo, (Object[]) null);

        return selectionDlg;
    }

    private static Collection<Element> getStereotypedElementCandidates(Stereotype stereotype) {

        final List<Element> stereotypedElements = new ArrayList<>();

        final Project projet = Application.getInstance().getProject();
        if (projet != null) {
            final Collection<Class> candidates = Finder.byTypeRecursively().find(projet, new java.lang.Class[]{Class.class}, false);
            for (Class c : candidates) {
                if (StereotypesHelper.hasStereotype(c, stereotype)) {
                    stereotypedElements.add(c);
                }
            }
        }
        return stereotypedElements;
    }
}
