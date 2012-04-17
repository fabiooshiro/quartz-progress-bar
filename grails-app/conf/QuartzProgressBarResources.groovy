modules = {
	underscore{
		dependsOn 'jquery'
		resource url: 'js/underscore-1.3.1.js'
	}
	backbone{
		dependsOn 'jquery, underscore'
		resource url: 'js/backbone.js'
	}
    marionette{
        dependsOn 'backbone'
        resource url: 'js/backbone.marionette.js'
    }
	backboneGrails{
		dependsOn 'jquery, backbone'
		resource url: 'js/backbone-grails.js'
	}
	namespacejs{
		dependsOn 'backboneGrails'
		resource url: 'js/namespace.js'
	}
	bootstrap{
		resource url: 'css/bootstrap/bootstrap.css'
		resource url: 'css/bootstrap/bootstrap-responsive.css'
		resource url: 'js/bootstrap.js'
	}
    trafficCop{
        dependsOn 'jquery'
        resource url: 'js/TrafficCop.min.js'
    }
    quartzProgressBarJs {
		dependsOn 'bootstrap, backboneGrails, namespacejs, marionette, trafficCop'
        resource url: 'js/quartz-progressbar.js'
    }
}