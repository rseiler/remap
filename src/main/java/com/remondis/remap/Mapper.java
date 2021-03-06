package com.remondis.remap;

import static com.remondis.remap.ReflectionUtil.getCollector;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class defines a reusable mapper object to perform multiple mappings for the configured object types.
 *
 * @param <S> The source type
 * @param <D> The destination type
 * @author schuettec
 */
public class Mapper<S, D> {

  private Mapping<S, D> mapping;

  Mapper(Mapping<S, D> mapping) {
    super();
    this.mapping = mapping;
  }

  Mapping<S, D> getMapping() {
    return mapping;
  }

  /**
   * Performs the mapping from the source to destination type.
   *
   * @param source The source object to map to a new destination object.
   * @return Returns a newly created destination object.
   */
  public D map(S source) {
    return mapping.map(source);
  }

  /**
   * Performs the mapping for the specified {@link Collection}.
   *
   * @param source The source collection to map to a new collection of destination objects.
   * @return Returns a newly created collection of destination objects. The type of the resulting collection is either
   *         {@link List} or {@link Set} depending on the specified type.
   */
  public Collection<D> map(Collection<S> source) {
    return _mapCollection(source);
  }

  /**
   * Performs the mapping for the specified {@link List}.
   *
   * @param source The source collection to map to a new collection of destination objects.
   * @return Returns a newly created list of destination objects.
   */
  public List<D> map(List<S> source) {
    return (List<D>) _mapCollection(source);
  }

  /**
   * Performs the mapping for the specified {@link Set}.
   *
   * @param source The source collection to map to a new collection of destination objects.
   * @return Returns a newly set list of destination objects.
   */
  public Set<D> map(Set<S> source) {
    return (Set<D>) _mapCollection(source);
  }

  /**
   * Performs the mapping for the elements provided by the specified {@link Iterable} .
   *
   * @param iterable The source iterable to be mapped to a new {@link List} of destination objects.
   * @return Returns a newly set list of destination objects.
   */
  public List<D> map(Iterable<S> iterable) {
    Stream<S> stream = StreamSupport.stream(iterable.spliterator(), false);
    return stream.map(this::map)
        .collect(Collectors.toList());
  }

  @SuppressWarnings("unchecked")
  private Collection<D> _mapCollection(Collection<S> source) {
    return (Collection<D>) source.stream()
        .map(this::map)
        .collect(getCollector(source));
  }

  @Override
  public String toString() {
    return mapping.toString();
  }

}
