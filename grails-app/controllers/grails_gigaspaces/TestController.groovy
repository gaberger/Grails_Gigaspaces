//package grails_gigaspaces
//
//import org.openspaces.core.GigaSpace
//
//
//class TestController {
//
//    def spaceControllerService
//
//    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
//
//    def index = {
//        redirect(action: "list", params: params)
//    }
//
//    def list = {
//        GigaSpace sp = spaceControllerService.initSpaceService("space")
//        flash.message = "Space Count " + sp.count(new Person())
//	    params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        Person[] results = sp.readMultiple(new Person(), params.max);
//    	[ personInstanceList: results, personInstanceTotal: sp.count(new Person()) ]
//    }
//
//
//    def create = {
//
////        def personInstance = new Person(params)
////        //personInstance.properties = params
////        sp.write(personInstance)
////        return [personInstance: personInstance]
//    }
//
//    def save = {
//        GigaSpace sp = spaceControllerService.initSpaceService("space")
//        //UUID id = UUID.randomUUID()
//
//        def personInstance = new Person(params)
//        //personInstance.id =  id
//
//        sp.write(personInstance)
//
//
//        flash.message = "ID = ${personInstance.getfirstName()}"
////        return [personInstance: personInstance]
////        if (personInstance.update()) {
////
////            flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
////            redirect(action: "show", id: personInstance.id)
////        }
////        else {
////            render(view: "create", model: [personInstance: personInstance])
////        }
//        redirect(action: "show", id: personInstance.firstName)
//    }
//
//    def show = {
//
//        GigaSpace sp = spaceControllerService.initSpaceService("space")
//       // def id = params.id
//          def personInstance = sp.readById(Person.class, params.firstName)
//        if (!personInstance) {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
//            redirect(action: "list")
//        }
//        else {
//            [personInstance: personInstance]
//        }
//    }
//
//    def edit = {
//        GigaSpace sp = spaceControllerService.initSpaceService("space")
//        def personInstance = sp.read(new Person(id: params.id))
//        if (!personInstance) {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
//            redirect(action: "list")
//        }
//        else {
//            return [personInstance: personInstance]
//        }
//    }
//
//    def update = {
//        GigaSpace sp = spaceControllerService.initSpaceService("space")
//       // def personInstance = sp.read(new Person(id: params.id))
//       //  def personInstance = sp.readById()
//        def personInstance = sp.readByID(Person.class , params.firstName);
//        if (personInstance) {
//            if (params.version) {
//                def version = params.version.toLong()
//                if (personInstance.version > version) {
//
//                    personInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'person.label', default: 'Person')] as Object[], "Another user has updated this Person while you were editing")
//                    render(view: "edit", model: [personInstance: personInstance])
//                    return
//                }
//            }
//            personInstance.properties = params
//            if (!personInstance.hasErrors() && sp.write(personInstance)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
//                redirect(action: "show", id: personInstance.id)
//            }
//            else {
//                render(view: "edit", model: [personInstance: personInstance])
//            }
//        }
//        else {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
//            redirect(action: "list")
//        }
//    }
//
//    def delete = {
//        GigaSpace sp = spaceControllerService.initSpaceService("space")
//        def personInstance = sp.readById(Person.class, params.firstName)
//        //def personInstance = sp.read(new Person(id: params.id))
//        if (personInstance) {
//            try {
//                sp.takeById(Person.class, params.firstName)
//                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
//                redirect(action: "list")
//            }
//            catch (Exception e) {
//                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
//                redirect(action: "show", id: params.id)
//            }
//        }
//        else {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
//            redirect(action: "list")
//        }
//    }
//}
