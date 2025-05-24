package t1.task6.dto;

import java.util.List;

public record  ListResponse<T>(List<T> items) {}
