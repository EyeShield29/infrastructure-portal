package com.infrastructure.portal.service.common;
//package com.rising.app.service.common;
//
//import java.io.File;
//import java.util.Locale;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Component;
//
//import freemarker.template.Configuration;
//import freemarker.template.TemplateExceptionHandler;
//
//@Component
//public class FreemarkerService implements InitializingBean {
//	private static final Logger LOGGER = LoggerFactory
//			.getLogger(FreemarkerService.class);
//	private static Configuration configuration;
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		configuration = new Configuration();
//		configuration.setDirectoryForTemplateLoading(new File(
//				FreemarkerService.class.getResource("/template").getPath()));
//		configuration.setDefaultEncoding("UTF-8");
//		configuration.setEncoding(Locale.CHINA, "UTF-8");
//		configuration.setWhitespaceStripping(true);
//		configuration.setClassicCompatible(true);
//		configuration.setURLEscapingCharset("UTF-8");
//		configuration
//				.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//	}
//
////	public String processOrderPhotoDoc(
////			OrderPhotoExportEntity orderPhotoExportEntity) {
////		String templateName = "order_photo_v1.ftl";
////		Map<String, Object> dataModel = new HashMap<String, Object>();
////		dataModel.put("orderInfo", orderPhotoExportEntity.getOrderInfo());
////		dataModel.put("photoDirList", orderPhotoExportEntity.getOrderPhotoDirVos());
////		StringWriter writer = new StringWriter();
////
////		try {
////			Template temp = configuration.getTemplate(templateName);
////			temp.process(dataModel, writer);
////		} catch (IOException e) {
////			LOGGER.error(String.format("创建模板[%s]失败", templateName), e);
////		} catch (TemplateException e) {
////			LOGGER.error(String.format("处理模板[%s]失败", templateName), e);
////		} finally {
////			writer.flush();
////			try {
////				writer.close();
////			} catch (IOException e) {
////				LOGGER.error(e.getMessage());
////			}
////		}
////
////		return writer.toString();
////	}
//}
