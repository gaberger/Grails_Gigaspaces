package grails_gigaspaces

import com.gigaspaces.annotation.pojo.SpaceClass
import com.gigaspaces.annotation.pojo.SpaceId
import com.gigaspaces.annotation.pojo.SpaceRouting


@SpaceClass
class Person {

    String id
    String firstName
    String lastName

@SpaceRouting
@SpaceId(autoGenerate = false)
   def  getfirstName() {
      return firstName
  }

   def setfirstName(firstName){
     this.firstName = firstName
   }


  static mapping = {
         id column:'firstName'
  }
}
