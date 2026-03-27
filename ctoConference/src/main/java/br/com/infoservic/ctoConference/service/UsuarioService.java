package br.com.infoservic.ctoConference.service;

import br.com.infoservic.ctoConference.dto.UsuarioCadastroDto;
import br.com.infoservic.ctoConference.dto.UsuarioExibicaoDto;
import br.com.infoservic.ctoConference.exception.NaoEncontradoException;
import br.com.infoservic.ctoConference.model.Usuario;
import br.com.infoservic.ctoConference.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioExibicaoDto gravar(UsuarioCadastroDto usuarioCadastroDto){
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioCadastroDto, usuario);
        return new UsuarioExibicaoDto(usuarioRepository.save(usuario));
    }

    public UsuarioExibicaoDto buscarPorId(Long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()){
            return new UsuarioExibicaoDto(usuarioOptional.get());
        } else {
            throw new NaoEncontradoException("Usuario não encontrado!");
        }
    }

    public List<UsuarioExibicaoDto> listarTodosUsuarios(){
        return usuarioRepository
                .findAll()
                .stream()
                .map(UsuarioExibicaoDto::new)
                .toList();
    }

    public void excluir(Long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()){
            usuarioRepository.delete(usuarioOptional.get());
        } else {
            throw new NaoEncontradoException("Usuario não encontrado!");
        }
    }

    public Usuario atualizar(Usuario usuario){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());
        if (usuarioOptional.isPresent()){
             return usuarioRepository.save(usuario);
        } else {
            throw new NaoEncontradoException("Usuario não encontrado!");
        }
    }

    public List<UsuarioExibicaoDto> buscarPeloNome (String termo){
        List<Usuario> usuarios = usuarioRepository
                .findByNomeContainingIgnoreCaseOrderByNomeAsc(termo);

        return usuarios.stream()
                .map(UsuarioExibicaoDto::new)
                .collect(Collectors.toList());
    }


}
