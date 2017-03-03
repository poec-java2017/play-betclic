/**
 * Created by xylphid on 01/02/17.
 */
$(function() {
    $('footer .title > span').on('click', function() {
        $('.legal').slideToggle();
    })
});

/**
 * Modal pari
 */
// Fonction de calcul automatique du gain potentiel
function betChange(){
    var quotation = Number($('.betModalQuotation > span:last-child').text().replace(',', '.'));

    var bet = Number($('#betInput').val());

    var sum = (quotation * bet).toFixed(2);

    $('.betModalSum').text(sum + ' €');
}

$('.betModalSubmit').click(function(){
    $.notify("Pari validé", "success");
    location.href = location.href + '#';
});

$('.betButton').click(function(){
    // Intitulé du match
    var title = $(this).parent().find('.info > .display').text();
    $('.betModalTitle').text(title);

    // Cote du match
    var quotation = $(this).text();
    $('.betModalQuotation > span:last-child').text(quotation);

    // Choix du pari
    var betOn = $(this).attr('data-bet');
    $('.betModalBetOn > span:last-child').text(betOn);

    betChange();
})

$('#betInput').change(betChange);
$('#betInput').keyup(betChange);
