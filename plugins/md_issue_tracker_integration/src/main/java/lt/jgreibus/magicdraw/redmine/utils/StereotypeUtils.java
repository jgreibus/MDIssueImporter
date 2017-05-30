package lt.jgreibus.magicdraw.redmine.utils;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

/**
 * Created by Justinas on 2017-05-30.
 */
public class StereotypeUtils {
    public static Stereotype getStereotypeObj(String profileName, String stereotypeName) {
        Project project = Application.getInstance().getProject();
        Profile profile = StereotypesHelper.getProfile(project, profileName);
        Stereotype stereotype = StereotypesHelper.getStereotype(project, stereotypeName, profile);
        return stereotype;
    }
}
