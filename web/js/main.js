var rootURL = 'http://localhost:8080/restExample/webresources/usuario';

$(document).ready(function() {
	listByParams($('#nameKey').val());
	labelElements = $(document).find('.elem-label');
	$(labelElements).each(function(index) {
		textElement = $(this).parent().find('.text');
		if (textElement.html()) {
			console.log('Teste');
		}
		console.log($(this).html())
		textElement.attr('placeholder', $(this).html());

	})
	if (!$('.text').val()) {
		element = $(this).parent().find('.elem-label');
		element.removeClass('hidden');
		element.addClass('active');
		$(this).removeAttr('placeholder');
	}
});

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

$('.button').click(function(e) {
	ripple($(this), e);
});

$('.menu-list a li').click(function(e) {
	ripple($(this), e);
});

$('#btnSave').click(function() {
	if($('#id').val()) {
		update();
	} else {
		insert();
	}
	
	return false;
});

$(document).on('click', '#userList a', function() {
	findById($(this).data('identity'));
	$('#btnDelete').show();
	return false;
});

$('#nameKey').keyup(function() {
	listByParams($('#nameKey').val());
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

function listAll() {
	console.log('Listando todos os usuários');
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
	console.log('Listando os usuários com nome: ' + nome);
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
	console.log('Procurando usuário...');
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
	console.log('Adicionando usuário...');
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
	console.log('Atualizando usuário...');
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
	console.log('Removendo usuário...');
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
	$('#gender').val(usuario.sexo);
}

function renderList(data) {
	list = data == null ? [] : (data instanceof Array ? data : [data]);
	$('#userList li').remove();
	$.each(list, function(index, user) {
		$('#userList').append('<li><a href="#" data-identity="' + user.id + '" >' + user.nome + '</a></li>');
	});
}