package quartz.progressbar;

public class QuartzProgressData {

	String id
	Integer total = 100
	Integer step = 0
	String msg
	String status
    
    Long version = 0
    
    void setStatus(String status){
        version ++
        this.@status = status
    }
    
    void setMsg(String msg){
        version ++
        this.@msg = msg
    }
    
    void setStep(Integer step){
        version ++
        this.@step = step
    }
    
    void setTotal(Integer total){
        version ++
        this.@total = total
    }
	
	long lastGet // last ms that controller get
}
