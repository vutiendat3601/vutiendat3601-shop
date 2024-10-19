import { AdminGuard } from './admin.guard';

describe('Admin', () => {
  it('should create an instance', () => {
    expect(new AdminGuard()).toBeTruthy();
  });
});
