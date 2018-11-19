package com.linepro.modellbahn.rest.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class FileUploadService {
	/**
	 * FileUploadService. Upload service for images
	 * 
	 * @author $Author:$ID
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @return
	*/
	
	@PUT
	@Path("produkt/{hersteller}/{bestellnr}/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response Image(@FormDataParam("FileName") InputStream fileName, 
		@FormDataParam("FileType") FormDataContentDisposition fileDetail,
		@FormDataParam("ImageData") InputStream uploadedImageData) {
		
		String uploadedImageLocation = "d://uploaded" + fileDetail.getFileName();
		/** Save the image file
		 * 
		 */
		writeToFile(fileName, fileDetail, uploadedImageData, uploadedImageLocation);
		
		String Output = "Image file uploaded to: " + uploadedImageLocation;
		
		return Response.status(2000).entity(Output).build();
	}

	private void writeToFile(InputStream fileName, FormDataContentDisposition fileDetail, InputStream uploadedImageData,
			String uploadedImageLocation) {
		// TODO Auto-generated method stub
		
	}
}