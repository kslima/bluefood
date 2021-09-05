package com.kslima.bluefood.domain.restaurante;

import com.kslima.bluefood.infrastructure.UploadConstraint;
import com.kslima.bluefood.util.FileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome nãpo pode ser vazio")
    @Size(max = 50)
    private String nome;

    @NotBlank(message = "A categoria não pode zer vazia")
    @Size(max = 25)
    private String categoria;

    @NotBlank(message = "A descrição não pode ser vazia")
    @Size(max = 80)
    private String descricao;

    @Size(max = 50)
    private String imagem;

    @NotNull(message = "O preço não pode ser vazio")
    @Min(0)
    private BigDecimal preco;

    @NotNull
    private Boolean destaque;

    @NotNull
    @ManyToOne
    private Restaurante restaurante;

    @Transient
    @UploadConstraint(acceptedTypes = {FileType.PNG, FileType.JPG}, message = "O arquivo não é um arquivo de imagem válido")
    private MultipartFile imagemFile;

    public void setImagemFileName() {
        if (getId() == null) {
            throw new IllegalArgumentException("O objeto precisa primeiro ser criado");
        }

        String extension = FileType.of(imagemFile.getContentType()).getExtension();
        this.imagem = String.format("%04d-comida.%s", getId(), extension);
    }
}
