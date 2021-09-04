package com.kslima.bluefood.domain.restaurante;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.kslima.bluefood.domain.Usuario;

import com.kslima.bluefood.infrastructure.UploadConstraint;
import com.kslima.bluefood.util.FileType;
import com.kslima.bluefood.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Restaurante extends Usuario{

    @NotBlank(message = "O CNPJ não pode ser vazio")
    @Pattern(regexp = "[0-9]{14}", message = "O CNPJ possui formato inválido")
    @Column(length = 14, nullable = false)
    private String cnpj;

    @UploadConstraint(acceptedTypes = {FileType.PNG, FileType.JPG}, message = "O arquivo de imagem é inválido")
    private transient MultipartFile logotipoFile;

    @Size(max = 80)
    private String logotipo;

    @NotNull(message = "A taxa de entrega não pode ser vazio")
    @Min(0)
    @Max(99)
    private BigDecimal taxaEntrega;

    @NotNull(message = "O tempo de entrega não pode ser vazio")
    @Min(0)
    @Max(99)
    private Integer tempoEntregaBase;

    @ManyToMany(fetch = FetchType.EAGER)
    @Size(min = 1, message = "O Restaurante precisa ter pelo menos uma categoria")
    @ToString.Exclude
    private Set<CategoriaRestaurante> categorias = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private Set<ItemCardapio> itensCardapio = new HashSet<>();

    public void setLogotipoFileName() {
        if (getId() == null) {
            throw new IllegalArgumentException("É preciso primeiro gravar o registro");
        }


        String extension = FileType.of(logotipoFile.getContentType()).getExtension();
        this.logotipo = String.format("%04d-logo.%s", getId(), extension);
    }

    public String categoriasAsText() {
        Set<String> strings = categorias.stream()
                .map(CategoriaRestaurante::getNome)
                .collect(Collectors.toSet());
        return StringUtils.concatenate(strings);

    }
    
}
