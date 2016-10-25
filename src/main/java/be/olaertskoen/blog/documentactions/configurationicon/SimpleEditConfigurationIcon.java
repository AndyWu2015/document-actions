package be.olaertskoen.blog.documentactions.configurationicon;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=com_liferay_document_library_web_portlet_DLAdminPortlet",
				"path=/document_library/view_file_entry"
		},
		service = PortletConfigurationIcon.class
)
public class SimpleEditConfigurationIcon extends BasePortletConfigurationIcon {

	private static final Log LOGGER = LogFactoryUtil.getLog(SimpleEditConfigurationIcon.class);

	@Reference private Portal portal;
	@Reference DLAppService dlAppService;

	public boolean isShow(PortletRequest portletRequest) {
		return true;
	}

	public String getMessage(PortletRequest portletRequest) {
		return "Open with SimpleEdit";
	}

	public String getURL(PortletRequest portletRequest, PortletResponse portletResponse) {
		HttpServletRequest servletRequest = portal.getHttpServletRequest(portletRequest);
		FileEntry fileEntry = retrieveFile(servletRequest);

		return "simpleedit://...";
	}

	private FileEntry retrieveFile(HttpServletRequest request) {
		try {
			long fileEntryId = ParamUtil.getLong(request, "fileEntryId");

			FileEntry fileEntry = null;

			if (fileEntryId > 0) {
				fileEntry = dlAppService.getFileEntry(fileEntryId);
			}

			if (fileEntry == null) {
				return null;
			}

			String cmd = ParamUtil.getString(request, Constants.CMD);

			if (fileEntry.isInTrash() && !cmd.equals(Constants.MOVE_FROM_TRASH)) {
				LOGGER.info("File entry is not supposed to be opened.");
				return null;
			}

			return fileEntry;
		} catch (PortalException e) {
			LOGGER.error("An exception ocurred while retrieving Simple Edit Url.", e);

			return null;
		}
	}
}