import { ValidateFieldPipe } from './validate-field.pipe';

describe('ValidateFieldPipe', () => {
  it('create an instance', () => {
    const pipe = new ValidateFieldPipe();
    expect(pipe).toBeTruthy();
  });
});
