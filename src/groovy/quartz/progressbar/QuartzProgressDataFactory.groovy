package quartz.progressbar;

public class QuartzProgressDataFactory {

	static final long TIME_DONE_2_KILL = 5000
	
	def allProgressData = [:]
	def nextId = 0
	def deathList = []
	
	static instance = new QuartzProgressDataFactory()
	
	static getInstance(){
		instance
	}
	
	QuartzProgressData create(id = null){
		QuartzProgressData quartzProgressData = new QuartzProgressData()
		
		// not mix ids
		if(id == null){
			quartzProgressData.id = 'q' + nextId++
		}else{
			quartzProgressData.id = 'u' + id
		}
		
		allProgressData.put(quartzProgressData.id, quartzProgressData)
		return quartzProgressData
	}
    
    def list(){
        def ls = []
        allProgressData.each{ k, v ->
            chkAlive(v)
            ls.add(v)
        }
        kill()
        return ls
    }
	
	void kill(){
		deathList.each{
			//println "kill ${it}"
			allProgressData.remove(it)
		}
	}
    
    void chkAlive(quartzProgressData){
        if(quartzProgressData){
            if(quartzProgressData.status == "done" || quartzProgressData.status == "error"){
                if(quartzProgressData.lastGet < System.currentTimeMillis() - TIME_DONE_2_KILL){
                    deathList.add(quartzProgressData.id)
                }else{
                    //println "${quartzProgressData.id} done. now waiting 4 the death"
                }
            }else{
                quartzProgressData.lastGet = System.currentTimeMillis()
            }
        }
    }
	
	QuartzProgressData get(String id){
		QuartzProgressData quartzProgressData = allProgressData.get(id)
        chkAlive(quartzProgressData)
        kill()
		return quartzProgressData
	}
	
}
