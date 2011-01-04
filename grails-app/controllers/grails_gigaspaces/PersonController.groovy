package grails_gigaspaces

import org.openspaces.core.GigaSpace



class PersonController {

    def spaceControllerService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
       GigaSpace sp = spaceControllerService.initSpaceService("space")
       flash.message = "Space Count " + sp.count(new Person())

       params.max = Math.min(params.max ? params.int('max') : 10, 100)
       Person[] results = sp.readMultiple(new Person(), params.max);

       [personInstanceList: results, personInstanceTotal: sp.count(new Person())]

    }

    def create = {
        def personInstance = new Person()
        personInstance.properties = params
        return [personInstance: personInstance]
    }

    def save = {
        GigaSpace sp = spaceControllerService.initSpaceService("space")
        def personInstance = new Person(params)
        if (sp.write(personInstance)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.firstName])}"
            redirect(action: "show", id: personInstance.firstName) //what is this
        }
        else {
            render(view: "create", model: [personInstance: personInstance])
        }
    }

    def show = {
        GigaSpace sp = spaceControllerService.initSpaceService("space")

        //def personInstance = Person.get(params.id)
        def personInstance = sp.readById(Person.class, params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
            [personInstance: personInstance]
        }
    }

    def edit = {
        GigaSpace sp = spaceControllerService.initSpaceService("space")
        //def personInstance = Person.get(params.id)
        def personInstance = sp.readById(Person.class, params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personInstance: personInstance]
        }
    }

    def update = {
        GigaSpace sp = spaceControllerService.initSpaceService("space")
        def personInstance = sp.readById(Person.class, params.id)

        if (personInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (personInstance.version > version) {
                    
                    personInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'person.label', default: 'Person')] as Object[], "Another user has updated this Person while you were editing")
                    render(view: "edit", model: [personInstance: personInstance])
                    return
                }
            }
            personInstance.properties = params
            if (!personInstance.hasErrors() && sp.write(personInstance)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), personInstance.firstName])}"
                redirect(action: "show", id: personInstance.firstName)
            }
            else {
                render(view: "edit", model: [personInstance: personInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        GigaSpace sp = spaceControllerService.initSpaceService("space")
        def personInstance = sp.readById(Person.class, params.id)

        if (personInstance) {
            try {
                sp.takeById(Person.class, params.id)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
    }
}
