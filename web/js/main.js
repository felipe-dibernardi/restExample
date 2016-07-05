var rootURL = 'http://localhost:8080/restExample/webresources/usuario';

$(document).ready(function() {
	listByParams($('#nameKey').val());
	labelElements = $(document).find('.elem-label');
	$(labelElements).each(function(index) {
		textElement = $(this).parent().find('.text');
		textElement.attr('placeholder', $(this).html());

	})
	if (!$('.text').val()) {
		element = $(this).parent().find('.elem-label');
		element.removeClass('hidden');
		element.addClass('active');
		$(this).removeAttr('placeholder');
	}
});

/* Função de abertura e fechamento do menu */

$('#menuButton').click(function() {
	var active = new RegExp('active')
	if (active.test($(this).attr('class'))) {
		$(this).removeClass('active');
		$(this).addClass('closed');
		$('#listMenuIcon').removeClass('closed');
		$('#listMenuIcon').addClass('active');
		$('#closeMenuIcon').removeClass('active');
		$('#closeMenuIcon').addClass('closed');
		$('#menuList').removeClass('active');
		$('#menuList').addClass('closed');
	} else {
		$(this).removeClass('closed');
		$(this).addClass('active');
		$('#listMenuIcon').removeClass('active');
		$('#listMenuIcon').addClass('closed');
		$('#closeMenuIcon').removeClass('closed');
		$('#closeMenuIcon').addClass('active');
		$('#menuList').removeClass('closed');
		$('#menuList').addClass('active');
	}
	
})

/* Função de alerta de nova mensagem no sistema */

$('#messageButton').click(function() {
	if ($('#messageButtonIcon').hasClass('animate')) {
		$('#messageButtonIcon').replaceWith($('#messageButtonIcon').clone(true));
	} else {
		$('#messageButtonIcon').addClass('animate');
	};
	if ($('#alertButtonIcon').hasClass('animate')) {
		$('#alertButtonIcon').replaceWith($('#alertButtonIcon').clone(true));
	} else {
		$('#alertButtonIcon').addClass('animate');
	};
	if ($('#messageCounter').hasClass('animate')) {
		$('#messageCounter').replaceWith($('#messageCounter').clone(true));
	} else {
		$('#messageCounter').addClass('animate');
	}
})

/* Funções para criar os placeholders e a barra nos input dinamicamente */

$('.text').focus(function() {
	element = $(this).parent().find('.elem-label');
	element.removeClass('hidden');
	element.addClass('active');
	$(this).removeAttr('placeholder');
});

$('.text').blur(function() {
	if (!$(this).val()) {
		element = $(this).parent().find('.elem-label');
		element.removeClass('active');
		element.addClass('hidden');
		$(this).attr('placeholder', element.html());
	}
	
})

/* Funções que aplicam o Ripple-effect */

$('.button').click(function(e) {
	ripple($(this), e);
});

$('.menu-list a li').click(function(e) {
	ripple($(this), e);
});

$(document).on('click', '#userList a', function() {
	findById($(this).data('identity'));
	$('#btnDelete').show();
	return false;
});

$('#nameKey').keyup(function() {
	listByParams($('#nameKey').val());
});

/* Ações ao serem tomadas em clique de botões */

$('#btnSave').click(function() {
	if($('#id').val()) {
		update();
	} else {
		insert();
	}
	return false;
});

$('#btnSearch').click(function() {
	findById($('#idKey').val());
	$('#btnDelete').show();
	$('#btnNew').show();
	$('#idSearch').addClass('search-label');
	return false;
});

$('#btnNew').click(function() {
	$('#searchKey').val('');
	renderDetails({});
	$('#btnNew').hide();
	$('#btnDelete').hide();
	$('#idSearch').removeClass('search-label');
	return false;
});

$('#btnDelete').click(function() {
	remove($('#id').val());
	renderDetails({});
	$('#searchKey').val('');
	$('#btnNew').hide();
	$('#btnDelete').hide();
	$('#idSearch').removeClass('search-label');
	return false;
})

/* Funções básicas do WebService */

function listAll() {
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: 'json',
		success: function(data) {
			renderList(data);
		}
	})
}

function listByParams(nome) {
	$.ajax({
		type: 'GET',
		url: rootURL + '/filtro?nome=' + nome,
		dataType: 'json',
		success: function(data) {
			renderList(data);
		}
	})
}

function findById(id) {
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id,
		dataType: 'json',
		success: function(data) {
			renderDetails(data);
		}
	});
}

function insert() {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: 'json',
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			listAll();
			alert('Usuário adicionado com sucesso.');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('Erro ao adicionar usuário.');
		}
	});
}

function update() {
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL,
		dataType: 'json',
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			listAll();
			alert('Usuário atualizado com sucesso.');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('Erro ao atualizar usuário.');
		}
	})
}

function remove(id) {
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + id,
		success: function(data, textStatus, jqXHR) {
			listByParams($('#nameKey').val());
			alert('Usuário removido com sucesso.');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('Erro ao remover usuário.');
		}
	});
}

/* Funções auxiliares do WebService */

function formToJSON() {
	return JSON.stringify({
		'id': $('#id').val() == '' ? null : $('#id').val(),
		'nome': $('#name').val(),
		'idade': $('#age').val(),
		'sexo': $('#male').is(':checked') ? $('#male').val() : $('#female').val()
	});
}

function renderDetails(usuario) {
	$('#id').val(usuario.id);
	$('#name').val(usuario.nome);
	$('#age').val(usuario.idade);
	if (usuario.sexo == 'Masculino') {
		$('#male').prop('checked', true);
	} else {
		$('#female').prop('checked', true);
	}
}

function renderList(data) {
	list = data == null ? [] : (data instanceof Array ? data : [data]);
	$('#userList li').remove();
	$.each(list, function(index, user) {
		$('#userList').append('<li><a href="#" data-identity="' + user.id + '" >' + user.nome + '</a></li>');
	});
}