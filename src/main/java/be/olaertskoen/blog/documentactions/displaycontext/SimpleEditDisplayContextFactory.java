package be.olaertskoen.blog.documentactions.displaycontext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.document.library.display.context.DLDisplayContextFactory;
import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;

@Component(
		immediate = true,
		service = DLDisplayContextFactory.class
)
public class SimpleEditDisplayContextFactory implements DLDisplayContextFactory {

	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext, HttpServletRequest request, HttpServletResponse response,
			DLFileEntryType dlFileEntryType) {
		return parentDLEditFileEntryDisplayContext;
	}

	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext, HttpServletRequest request, HttpServletResponse response,
			FileEntry fileEntry) {
		return parentDLEditFileEntryDisplayContext;
	}

	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext, HttpServletRequest request,
			HttpServletResponse response, FileShortcut fileShortcut) {
		return parentDLViewFileVersionDisplayContext;
	}

	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext, HttpServletRequest request,
			HttpServletResponse response, FileVersion fileVersion) {
		return new SimpleEditDisplayContext(parentDLViewFileVersionDisplayContext.getUuid(),parentDLViewFileVersionDisplayContext, request, response, fileVersion);
	}
}