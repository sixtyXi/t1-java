package t1.task7.dto;

import java.util.List;

public record ListResponse<T>(List<T> items) {}
