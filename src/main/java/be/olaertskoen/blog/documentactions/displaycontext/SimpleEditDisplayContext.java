package be.olaertskoen.blog.documentactions.displaycontext;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLToolbarItem;
import com.liferay.portal.kernel.settings.*;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;

public class SimpleEditDisplayContext extends BaseDLViewFileVersionDisplayContext {

	private static final Log LOGGER = LogFactoryUtil.getLog(SimpleEditDisplayContext.class);

	private ThemeDisplay themeDisplay;

	public SimpleEditDisplayContext(UUID uuid, DLViewFileVersionDisplayContext parentDLDisplayContext, HttpServletRequest request, HttpServletResponse response, FileVersion fileVersion) {
		super(uuid, parentDLDisplayContext, request, response, fileVersion);

		this.themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);;
	}

	public Menu getMenu() throws PortalException {
		Menu menu = super.getMenu();

		if (showAction()) {
			URLMenuItem urlMenuItem = new URLMenuItem();
			urlMenuItem.setMethod(HttpMethods.GET);
			urlMenuItem.setKey("open-with-simple-edit");
			urlMenuItem.setLabel("Open with SimpleEdit");
			urlMenuItem.setURL("simpleedit://...");

			menu.getMenuItems().add(urlMenuItem);
		}

		return menu;
	}

	private boolean showAction() throws SettingsException {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
			return true;
		}

		Settings settings = SettingsFactoryUtil.getSettings(new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getId()));
		TypedSettings typedSettings = new TypedSettings(settings);

		return typedSettings.getBooleanValue("showActions");
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = super.getToolbarItems();

		URLToolbarItem toolbarItem = new URLToolbarItem();
		toolbarItem.setKey("open-with-simple-edit");
		toolbarItem.setLabel("Open with SimpleEdit");
		toolbarItem.setURL("simpleedit://...");

		toolbarItems.add(toolbarItem);

		return toolbarItems;
	}
}