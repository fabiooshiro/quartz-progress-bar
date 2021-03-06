Quartz Progressbar Grails Plugin
================================

Controller code:

```groovy
def save() {
    def myDomainInstance = new MyDomain(params)
    if (!myDomainInstance.save(flush: true)) {
        render(view: "create", model: [myDomainInstance: myDomainInstance])
        return
    }
    def progBarId = QuartzProgressBar.execute{ ctx, progressBar ->
        progressBar.total = 42
        42.times{
            progressBar.step = it + 1
            progressBar.msg = "step ${progressBar.step} of ${progressBar.total}"
            sleep(128)
        }
        progressBar.msg = "done"
    }
    def progBarId2 = QuartzProgressBar.execute{ ctx, progressBar ->
        //ctx.myService.myMethod()
        progressBar.total = 42
        12.times{
            progressBar.step = it + 1
            progressBar.msg = "step ${progressBar.step} of ${progressBar.total}"
            sleep(256)
        }
        progressBar.msg = "done"
        throw new RuntimeException("some error")
    }
    flash.message = message(code: 'default.created.message', args: [message(code: 'myDomain.label', default: 'MyDomain'), myDomainInstance.id])
    redirect(action: "show", id: myDomainInstance.id, params : [progBarId: progBarId, progBarId2: progBarId2])
}
```

View code:

```html
<script type="text/javascript">
    var config = {
        contextPath: "${request.contextPath}"
    };
</script>
<g:quartzProgressBar id="${params.progBarId }" />
<g:quartzProgressBar id="${params.progBarId2 }" />
```

How to get quartzProgressData object?

```groovy
package quartz.progress.bar.sample

public class MySampleJob {

    static triggers = {
        cron name: 'MySampleProgressJob', cronExpression: "1/120 * * * * ?"
    }
    
    def group = 'MySampleJobs'
    
    def grailsApplication

    def execute(context) {
        def progressData = context.mergedJobDataMap.get("quartzProgressData")
        progressData.total = 331
        331.times{
            sleep(512)
            progressData.step = it + 1
            progressData.msg = "Step ${progressData.step} of ${progressData.total}"
            println "Step ${progressData.step} of ${progressData.total}"
        }
        progressData.msg = "Done..."
    }
}
```

All jobs running:

```html
<script type="text/javascript">
    var config = {
        contextPath: "${request.contextPath}"
    };
</script>
<g:quartzProgressBarList />
```



Sample grails project at: https://github.com/fabiooshiro/quartz-progress-bar-sample

