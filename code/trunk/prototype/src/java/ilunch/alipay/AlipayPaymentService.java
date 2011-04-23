package ilunch.alipay;

import java.util.HashMap;
import java.util.Map;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayFunction;
import com.alipay.util.AlipayNotify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AlipayPaymentService {
	
	/**
	 * 
	 * @param orderId - The unique order ID
	 * @param orderSubject - The order title. If set to null or empty string, order title is default to "订单"+orderId
	 * @param totalFee
	 * @return UTF-8 url-encoded payment request URL
	 */
	public static String getPaymentURL(String orderId, String orderSubject, double totalFee) {
		if(orderId == null || orderId.isEmpty() || totalFee <= 0)
			return null;
		if(orderSubject == null || orderSubject.isEmpty())
			orderSubject = "订单"+orderId;
		
		String params = buildParams(
				AlipayConfig.partner,
				AlipayConfig.seller_email,
				AlipayConfig.return_url,
				AlipayConfig.notify_url,
				orderId,
				orderSubject,
				String.format("%.2f", totalFee),
				AlipayConfig.input_charset,
				AlipayConfig.key,
				AlipayConfig.sign_type
				);
		
		return AlipayConfig.api_url+params;
	}
	
	/**
	 * 
	 * @param params
	 * @return null for fail. orderId for success
	 */
	public static String parseResponse(Map<String, String> params) {
		if(params == null || params.isEmpty())
			return null;
		
		String sign = params.get("sign");
		String orderId = params.get("out_trade_no");
		if(sign == null || orderId == null)
			return null;
		
		String mysign = AlipayNotify.GetMysign(params, AlipayConfig.key);
		if(!mysign.equals(sign))
			return null;
		
		if(params.get("is_success").equalsIgnoreCase("T") && params.get("trade_status").equalsIgnoreCase("TRADE_FINISHED"))
			return orderId;
		return null;
	}
	
	private static String buildParams(
			String partner,
			String seller_email,
			String return_url,
			String notify_url,
			//String show_url,
			String out_trade_no,
			String subject,
			//String body,
			String total_fee,
			//String paymethod,
			//String defaultbank,
			//String anti_phishing_key,
			//String exter_invoke_ip,
			//String extra_common_param,
            //String buyer_email,
			//String royalty_type,
			//String royalty_parameters,
            String input_charset,
            String key,
            String sign_type) {
		
		
		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("service","create_direct_pay_by_user");
		sPara.put("payment_type","1");
		sPara.put("partner", partner);
		sPara.put("seller_email", seller_email);
		sPara.put("return_url", return_url);
		sPara.put("notify_url", notify_url);
		sPara.put("_input_charset", input_charset);
		//sPara.put("show_url", show_url);
		sPara.put("out_trade_no", out_trade_no);
		sPara.put("subject", subject);
		//sPara.put("body", body);
		sPara.put("total_fee", total_fee);
		//sPara.put("paymethod", paymethod);
		//sPara.put("defaultbank", defaultbank);
		//sPara.put("anti_phishing_key", anti_phishing_key);
		//sPara.put("exter_invoke_ip", exter_invoke_ip);
		//sPara.put("extra_common_param", extra_common_param);
		//sPara.put("buyer_email", buyer_email);
		//sPara.put("royalty_type", royalty_type);
		//sPara.put("royalty_parameters", royalty_parameters);
		
		Map<String, String> sParaNew = AlipayFunction.ParaFilter(sPara);
		String mysign = AlipayFunction.BuildMysign(sParaNew, key);
		sParaNew.put("sign", mysign);
		sParaNew.put("sign_type", "MD5");
		
		return AlipayFunction.CreateEncodedLinkString(sParaNew);
		
	}
	
	public static void main(String[] args) {
		System.out.println(AlipayPaymentService.getPaymentURL("2011013546", "阿波罗一号", 3));
	}
	
}
