package spotitube.mappers;

import java.util.List;

public interface IMapper<T1, T2>
{
    /**
     * Converts a DTO to a Domain Entity.
     *
     * @param dto  The DTO to convert.
     * @param args Optional arguments.
     * @return A converted Domain Entity.
     */
    T1 convertToEntity(T2 dto, Object... args);

    /**
     * Converts a Domain Entity into a DTO.
     *
     * @param entity The Domain Entity to convert.
     * @param args   Optional arguments.
     * @return A converted DTO.
     */
    T2 convertToDTO(T1 entity, Object... args);

    /**
     * Converts a list of DTOs to a list of Domain Entities.
     *
     * @param dtos The list of DTOs to convert.
     * @param args Optional arguments.
     * @return A converted list of Domain Entities.
     */
    List<T1> convertToEntity(List<T2> dtos, Object... args);

    /**
     * Converts a list of Domain Entities to a list of DTOs.
     *
     * @param entities The list of Domain Entities to convert.
     * @param args     Optional arguments.
     * @return A converted list of DTOs.
     */
    List<T2> convertToDTO(List<T1> entities, Object... args);
}
