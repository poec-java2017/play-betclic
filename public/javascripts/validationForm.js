/**
 * Created by formation2 on 08/02/17.
 */
$(document).on('submit', '#registerForm', function(e){
    e.preventDefault();
   if (document.registerForm.Password.value != document.registerForm.PasswordConfirm.value){
       alert( "Veuillez  renseignez deux mots de passe identiques!" );
       document.registerForm.Password.focus() ;
       return false;
   }


    // var nombre = document.registerForm.PhoneNumber.value;
    // var chiffres = new String(nombre);
    // // Enlever tous les charactères sauf les chiffres
    // //chiffres = chiffres.replace(/[^0-9]/g, '');
    //
    // if (isNaN(parseFloat(nombre))) {
    //     alert("Assurez-vous de rentrer un nombre");
    //     document.registerForm.PhoneNumber.focus();
    //     return false;
    // }
    //
    // // Nombre de chiffres
    // compteur = chiffres.length;
    // if (compteur!=10)
    // {
    //     alert("Assurez-vous de rentrer un numéro à 10 chiffres (xxx-xxx-xxxx)");
    //     document.registerForm.PhoneNumber.focus();
    //     return false;
    // }

    //Test pour la majorité
    var object = document.registerForm.CustBirthDate_Year;
    var index = object.selectedIndex;
    var value =  object.options[index].value;
    if($('#CustBirthDate_Year').value == 1999){
        alert("Vous êtes trop jeune pour être autorisé à utilise un site de paris en ligne." +
            "Revenez lorsque vous aurez 18 ans");
        return false;
    }

    $(this).submit();
});