package com.atracker.core.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.atracker.core.AtrackerContext;
import com.atracker.core.enums.ACTION;
import com.atracker.core.enums.LEVEL;
import com.atracker.core.enums.MODEL;
import com.atracker.service.BasicIdGeneratorService;

/**
 * 
 * Process method stack
 * <pre>
 * -----------------------Frontend.Request----------------------
 * |				^			|								^
 * V				|     		|								| 
 * ----backend.call--    		|								| 
 *  parentId:1,spanId:2			V								| 
 *    							-----Backend.Dosomthing----------	
 *							   	 |	|	parenId:1		 ^	^
 *								 |	V	spanId:3		 |	| 
 *								 |	------Helper.Call-----	| 
 * 								 |    parentId:3			| 
 * 								 V 	  spanId:4				| 
 * 								 ------- Helper.call--------
 *									parentId:3 spanId:5	
 * 									
 * </pre>
 * 
 * 
 * @author Robin							
 *
 */
public class DefaultAtrackerContext extends AtrackerContext {

	private static final ThreadLocal<AtrackerContext> atrackinfo = new ThreadLocal<AtrackerContext>();
	private static volatile BasicIdGeneratorService generate= new BasicIdGeneratorService();
	private static final String UNDERLINE = "_";
	private static final int METHODLEVEL =3;//trackerInfo>->trackerInfo(MODEL.DEFAULT, ACTION.UNKNOWE,LEVEL.TRACK,info )>
	private volatile int parentId = 0;
	private volatile int spanId = 0;
	private volatile boolean isrollback = false;
	private StackTraceElement currentMethod;
	private volatile int lastspanId = 0;
	private volatile int lastparentId = 0;
	private volatile int startPoint=0;
	private volatile String trackerId;
	private Map<String, Map<String,String>> parentsMethod = new HashMap<String, Map<String,String>>();//method-<fulmethod,spanId_parentId>;

	public DefaultAtrackerContext() {
		this(0);
	}
	public DefaultAtrackerContext(int start) { 
		this.startPoint=start;
		this.trackerId = generate.generate(); 
		DefaultAtrackerContext.atrackinfo.set(this);
	}
	

	public void setCurrentContext(StackTraceElement[] allMethod) {
		setCurrentContext(allMethod,METHODLEVEL);
	}
	public void setCurrentContext(StackTraceElement[] allMethod,int methodLevel) {

		String spanStr = validateParentMethod(allMethod);//查看是是否存在于父类中，如果在，查看当前方法时候已经被调用过，调用过返回调用的parentID spanId-1需要自加
		int spanID=Integer.valueOf(spanStr.split(UNDERLINE)[0]);//span method id
		int parentID=Integer.valueOf(spanStr.split(UNDERLINE)[1]);//parent method id
		currentMethod=allMethod[methodLevel];//current method
		String method=keyMethodName(currentMethod); 
		Map<String, String> methodValue=getOrCreate(currentMethod);
		//System.out.println("spanID" + spanID);
		if(isrollback){
			this.spanId=lastspanId;	
			this.parentId=this.lastparentId; 
			 isrollback=false;
		}
		if (spanID == -1 && parentID==0) {
			parentId = startPoint; // is root
			this.spanId = spanId + 1;
			methodValue.put(currentMethod.toString(), spanId+UNDERLINE+parentId);
			parentsMethod.put(method, methodValue); 
		} else if(spanID==-1 && parentID!=0){ //  a method exit in stack
			this.spanId = spanId + 1;
			methodValue.put(currentMethod.toString(), spanId+UNDERLINE+parentID);
			parentsMethod.put(method, methodValue); 
		}
		else{// is sub method
			if (spanID <= this.spanId && !isCurrentMethodExits(allMethod)) { // not same method
				parentId = spanID+startPoint;
		 	    this.spanId = spanId + 1;
		 		methodValue.put(currentMethod.toString(), spanId+UNDERLINE+parentId);
				parentsMethod.put(method, methodValue); 
			}else if(isCurrentMethodExits(allMethod)) { // not same method
			 	 lastparentId=parentID;
				 lastspanId=spanID;
				 parentId = parentID;
			 	 this.spanId = spanID;
			 	 isrollback=true; 
			 	 //	parentsMethod.put(allMethod[1].getClassName() + "."+ allMethod[1].getMethodName(), spanId+UNDERLINE+parentId);  
			} 
		}

	}

	public int getSpanID() {
		return this.spanId;
	}

	public int getParentId() {
		return this.parentId;
	}

	public String getTrackerID() {
		return this.trackerId;
	}
	public StackTraceElement getCurrentMethod() {
		return this.currentMethod;
	}

	private String keyMethodName(StackTraceElement mthod ){
		return mthod.getClassName() + "."+mthod.getMethodName();
	}
	
	/**
	 * get parentId
	 * 
	 * @param allMethods
	 * @return
	 */
	private String validateParentMethod(StackTraceElement[] allMethods) {
		String ret = "-1_"+startPoint+"";
		if (allMethods.length > 2) {
			for (int i = 2; i < allMethods.length; i++) {
				String valueRet= getSpanIdParentId(allMethods[i]);//if not eq -1_0 then parent exit
				if(!ret.equals(valueRet)){ 
					boolean exit=isCurrentMethodExits(allMethods);// check current method eixts or not 
					if (exit) {
						ret = getSpanIdParentId(allMethods[1]);//get fat
					} else {
						ret = valueRet;
					}
					break;
				}
			}
		} else if (allMethods.length == 2) { 
			return getSpanIdParentId(allMethods[1]); 
		}
		return ret;
	}
	
	private String getSpanIdParentId(StackTraceElement methodName){
		String ret = "-1_"+startPoint+"";
		String keyMethodName=keyMethodName(methodName);
		Map<String,String> methodValue= parentsMethod.get(keyMethodName); 
		if (methodValue!=null) { 
			String key=null;
			for(Iterator<String> it=methodValue.keySet().iterator();it.hasNext();){
				key=it.next(); 
				if(methodName.toString().startsWith(keyMethodName)&& !methodName.toString().equals(key)){
					String value=methodValue.get(key); 
					ret=value!=null?value:ret; 
				}else if(methodName.toString().startsWith(keyMethodName)&& methodName.toString().equals(key)){
					String value=methodValue.get(key); 
					ret=value!=null?"-1"+UNDERLINE+value.split(UNDERLINE)[1]:ret; 
					break;
				}
				}
		}
		 
		return ret;
	}

	private boolean isCurrentMethodExits(StackTraceElement[] allMethods) {//检查当前方法是否被记录过。
		Map<String,String> value = this.parentsMethod.get(keyMethodName(allMethods[1])); 
		return value!=null && value.size()>0 ?true :false;
	}
	
	/**
	 * get or create a hashMap for a method.
	 * @param method
	 * @return
	 */
	private Map<String,String> getOrCreate(StackTraceElement method){
			String methodName=keyMethodName(method);
			Map<String,String> value=	parentsMethod.get(methodName)	; 
			if(value==null){
				Map<String,String> newValue=new HashMap<>();
				parentsMethod.put(methodName, newValue);
				return newValue;
			}else{
				return value;
			} 
	}

	@Override
	public String getCurrentMethodName() {
		// TODO Auto-generated method stub
		return keyMethodName(this.currentMethod);
	}
}
